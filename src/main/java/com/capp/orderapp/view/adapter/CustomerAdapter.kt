package com.capp.orderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.capp.orderapp.R
import com.capp.orderapp.databinding.RecyclerItemBinding
import com.capp.orderapp.model.Customers
import com.capp.orderapp.view.ListCustomerFragmentDirections
import kotlinx.android.synthetic.main.recycler_item.view.*

class CustomerAdapter(var customerlist:ArrayList<Customers>):
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>(),CustomerClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        var inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RecyclerItemBinding>(inflater,
            R.layout.recycler_item,parent,false)

        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.view.customers=customerlist[position]
        holder.view.listener=this
    }

    override fun getItemCount(): Int {
    return customerlist.size
    }

    class CustomerViewHolder(var view: RecyclerItemBinding):RecyclerView.ViewHolder(view.root){

    }

    fun uptadeList(list: ArrayList<Customers>){
        customerlist.clear()
        customerlist.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCustomerClicked(view: View) {
        var action= ListCustomerFragmentDirections.actionListCustomerFragmentToCustomerDetailFragment(view.customersIdTextView.text.toString().toInt())
         Navigation.findNavController(view).navigate(action)
    }
    //{"address":"WAYNE MANOR  ","city":"GOTHAM CITY","firstName":"BRUCE","lastName":"WAYNE","phoneNumber":"17499353773","state":"DE","zip":"976427","zipSuffix":"1072"}
}