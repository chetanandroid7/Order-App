package com.capp.orderapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.capp.orderapp.model.Customers
import com.capp.orderapp.model.SearsResponse
import com.capp.orderapp.service.CustomerAPIService
import com.capp.orderapp.service.CustomerDatabase
import com.capp.orderapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListCustomerViewModel(application: Application) : BaseViewModel(application) {

    private val customerAPIService = CustomerAPIService()
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    val response = MutableLiveData<List<Customers>>()
    val responseErrr = MutableLiveData<Boolean>()
    val responseLoading = MutableLiveData<Boolean>()

    fun getData() {
        val prefTime = customPreferences.getTime()
        if (prefTime != null && prefTime != 0L) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    fun refreshDataFromAPI() {
    getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        responseLoading.value=true
        launch {
            val customers = CustomerDatabase(getApplication()).customersDao().getAllCustmers()
            passDataToLiveData(customers)
        }
    }

    private fun getDataFromAPI() {
        responseLoading.value = true

        disposable.add(
            customerAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearsResponse>() {
                    override fun onSuccess(t: SearsResponse) {
                        storeInSQLite(t.customers)
                    }

                    override fun onError(e: Throwable) {
                        responseErrr.value = true
                        responseLoading.value = false
                        e.printStackTrace()
                    }



                })
        )
    }

    private fun passDataToLiveData(customers: List<Customers>) {


        response.value = customers
        responseLoading.value = false
        responseErrr.value = false
    }

    private fun storeInSQLite(list: List<Customers>) {
        launch {
            val dao = CustomerDatabase(getApplication()).customersDao()
            dao.deleteAllcustomers()
            val longList = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < longList.size) {
                list[i].customerId = longList[i].toInt()
                i++
            }
            passDataToLiveData(list)
        }
        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}