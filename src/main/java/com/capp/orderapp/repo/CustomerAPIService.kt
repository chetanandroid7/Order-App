package com.capp.orderapp.service

import com.capp.orderapp.model.SearsResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CustomerAPIService {
    private val BASE_URL="https://run.mocky.io/";
    private val  api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AppAPI::class.java)

    fun getData(): Single<SearsResponse>{
        return api.getSearsResponse()
    }
}