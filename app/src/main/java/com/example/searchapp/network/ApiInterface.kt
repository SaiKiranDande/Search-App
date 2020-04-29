package com.example.airtelapp.network


import com.example.searchapp.data.AddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("address/autocomplete")
    fun getAddressData(
        @Query("queryString") queryString: String,
        @Query("city") city: String
    ): Call<AddressResponse>

}