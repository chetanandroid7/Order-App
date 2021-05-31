package com.capp.orderapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {
    companion object{
        private var sharedPreferences :SharedPreferences?=null

        private var instance:CustomSharedPreferences?=null
        val lock=Any()

        operator fun invoke(context: Context):CustomSharedPreferences = instance ?: synchronized(lock){
            instance?: makeCustomSharedPreferences(context).also {
                instance=it
            }
        }
        private fun makeCustomSharedPreferences(context: Context):CustomSharedPreferences   {
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }
    fun getTime()= sharedPreferences?.getLong("time",0)
    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong("time",time)
        }
    }
}