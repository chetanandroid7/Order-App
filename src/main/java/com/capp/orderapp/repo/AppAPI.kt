package com.capp.orderapp.service

import com.capp.orderapp.model.SearsResponse
import io.reactivex.Single
import retrofit2.http.GET

//https://run.mocky.io/v3/eecc4707-ea59-4be3-b00d-eda5a2240493
interface AppAPI {
    @GET("v3/eecc4707-ea59-4be3-b00d-eda5a2240493")
    fun getSearsResponse():Single<SearsResponse>
}