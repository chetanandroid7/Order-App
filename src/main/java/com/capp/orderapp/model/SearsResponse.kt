package com.capp.orderapp.model

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.*


data class SearsResponse(
    @SerializedName("firstName") var firstName : String="",
    @SerializedName("lastName") var lastName : String="",
    @SerializedName("phoneNumber") var phoneNumber : String="",
    @SerializedName("customers")  var customers : List<Customers> = listOf()

)

@Entity
data class Customers (
    @SerializedName("orderRef") var orderRef : String="",
    @SerializedName("status") var status : String="",
    @SerializedName("scheduleDate") var scheduleDate : String="",
    @SerializedName("scheduleStartTime") var scheduleStartTime : String="",
    @SerializedName("scheduleEndTime") var scheduleEndTime : String="",
    @SerializedName("customer") var customer : Customer,
    @SerializedName("serviceRequested") var serviceRequested : String="",
    @SerializedName("serviceSpecialInstructions") var serviceSpecialInstructions : String="",
    @SerializedName("jobIndicator") var jobIndicator : String="",
    @SerializedName("imageUrl") var imageUrl : String=""
)
{
    @PrimaryKey(autoGenerate= true)
    var customerId:Int=0
}

data class Customer (

    @SerializedName("firstName") var firstName : String,
    @SerializedName("lastName") var lastName : String,
    @SerializedName("address") var address : String,
    @SerializedName("city") var city : String,
    @SerializedName("state") var state : String,
    @SerializedName("zip") var zip : String,
    @SerializedName("zipSuffix") var zipSuffix : String,
    @SerializedName("phoneNumber") var phoneNumber : String

)
class CustomerConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): Customer {

        val listType = object : TypeToken<Customer>() {

        }.type

        return gson.fromJson<Customer>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: Customer): String {
        return gson.toJson(someObjects)
    }
}


