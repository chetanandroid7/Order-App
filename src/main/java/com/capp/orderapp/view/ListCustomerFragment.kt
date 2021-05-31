package com.capp.orderapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capp.orderapp.R
import com.capp.orderapp.adapter.CustomerAdapter
import com.capp.orderapp.model.Customers
import com.capp.orderapp.viewmodel.ListCustomerViewModel
import kotlinx.android.synthetic.main.fragment_customer_list.*


class ListCustomerFragment : Fragment() {
    private  var adapter= CustomerAdapter(arrayListOf())
    private lateinit var viewModel: ListCustomerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            viewModel=ViewModelProvider(this).get(ListCustomerViewModel::class.java)
            viewModel.getData()

            recyclerViewList.layoutManager=LinearLayoutManager(context)
            recyclerViewList.adapter=adapter
            observeLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            recyclerViewList.visibility=View.INVISIBLE
            textError.visibility=View.INVISIBLE
            loading.visibility=View.VISIBLE
            viewModel.refreshDataFromAPI()
            swipeRefreshLayout.isRefreshing=false
        }

    }
    fun observeLiveData(){
        viewModel.response.observe(viewLifecycleOwner, Observer { customers->
            customers?.let {
                recyclerViewList.visibility=View.VISIBLE
                adapter.uptadeList(it as ArrayList<Customers>)
            }
        })
        viewModel.responseErrr.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if (it){
                    textError.visibility=View.VISIBLE
                    recyclerViewList.visibility=View.INVISIBLE
                }else{
                    textError.visibility=View.INVISIBLE
                }
            }
        })
        viewModel.responseLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    loading.visibility=View.VISIBLE
                    recyclerViewList.visibility=View.INVISIBLE
                }else{
                    loading.visibility=View.INVISIBLE

                }
            }
        })
    }
}