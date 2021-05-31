package com.capp.orderapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.capp.orderapp.R
import com.capp.orderapp.adapter.FinishClickListener
import com.capp.orderapp.databinding.FragmentCustomerDetailsBinding
import com.capp.orderapp.viewmodel.CustomerViewModel

class CustomerDetailsFragment : Fragment(),FinishClickListener {
    private lateinit var viewModel : CustomerViewModel
    private lateinit var databinding : FragmentCustomerDetailsBinding
    var customerId =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        databinding=DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details,container,false)
        databinding.listener=this
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            customerId=CustomerDetailsFragmentArgs.fromBundle(it).customerid

        }
        viewModel=ViewModelProvider(this).get(CustomerViewModel::class.java)
        viewModel.getDataFromRoom(customerId)

        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.customers.observe(viewLifecycleOwner, Observer { customers->
            customers?.let {
                    databinding.customers=customers
            }
        })
    }

    override fun onFinishClicked(view: View) {
        viewModel.updateRoomStatusOnFinish(customerId)
        Navigation.findNavController(view).navigateUp()
    }


}