package com.example.searchapp.model

import com.example.airtelapp.network.ApiClient
import com.example.searchapp.data.AddressResponse
import com.example.searchapp.listener.ApiResponseListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel {

    val BASE_URL = "https://digi-api.airtel.in/compassLocation/rest/"

    fun getResponseData(query: String, city: String, listener: ApiResponseListener) {
        ApiClient.getApi(BASE_URL).getAddressData(query, city)
            .enqueue(object : Callback<AddressResponse> {
                override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                    listener.onFailuer(t)
                }

                override fun onResponse(
                    call: Call<AddressResponse>, response: Response<AddressResponse>
                ) {
                    if (response.isSuccessful) {
                        listener.onSuccess(response.body())
                    } else {
                        listener.onError(response.message())
                    }
                }

            })
    }
}