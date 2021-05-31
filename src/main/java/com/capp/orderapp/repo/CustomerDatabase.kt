package com.capp.orderapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capp.orderapp.model.Customers
import com.capp.orderapp.model.CustomerConverter

@Database(entities = arrayOf(Customers::class),version = 1)
@TypeConverters(CustomerConverter::class)
abstract class CustomerDatabase :RoomDatabase()  {
    abstract fun customersDao():CustomerDao

    companion object{
        @Volatile private var instance: CustomerDatabase?= null
        private val lock=Any()
        operator fun invoke(context:Context) = instance ?:  synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it
            }
        }
        private fun makeDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,CustomerDatabase::class.java,"customersDatabase"
        ).build()
    }
}