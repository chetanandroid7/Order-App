package com.capp.orderapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capp.orderapp.model.Customers
import com.capp.orderapp.model.SearsResponse

@Dao
interface CustomerDao {


    @Insert
    suspend fun insertAll(vararg customers: Customers) : List<Long>

    @Query("SELECT * FROM customers")
    suspend fun getAllCustmers(): List<Customers>

    @Query("SELECT * FROM customers WHERE customerId=:customerId")
    suspend fun getCustmer(customerId: Int):Customers

    @Query("DELETE FROM customers")
    suspend fun deleteAllcustomers()

    @Query("UPDATE customers SET status='CO' WHERE customerId=:customerId")
    suspend fun uodateCustomerOnFinish(customerId: Int)
}