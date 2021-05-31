package com.capp.orderapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.capp.orderapp.model.Customers
import com.capp.orderapp.service.CustomerDatabase

import kotlinx.coroutines.launch

class CustomerViewModel(application: Application) : BaseViewModel(application) {
      var customers=MutableLiveData<Customers>()

    fun getDataFromRoom(id:Int){
       launch {
           val dao= CustomerDatabase(getApplication()).customersDao()
           customers.value=dao.getCustmer(id)
       }
    }

    fun updateRoomStatusOnFinish(id:Int){
        launch {
            val dao= CustomerDatabase(getApplication()).customersDao()
            dao.uodateCustomerOnFinish(id)
        }
    }
}