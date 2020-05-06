package com.example.searchapp.model

import com.example.airtelapp.network.ApiClient
import com.example.searchapp.data.AddressResponse
import com.example.searchapp.listener.ApiResponseListener
import com.example.searchapp.listener.CommonNetworkCallback

class HomeModel {

    val BASE_URL = "https://digi-api.airtel.in/compassLocation/rest/"

    fun getResponseData(query: String, city: String, listener: ApiResponseListener) {
        ApiClient.getApi(BASE_URL).getAddressData(query, city)
            .enqueue(object : CommonNetworkCallback<AddressResponse>() {
                override fun onApiResponseSuccess(apiResponse: ApiResponse<AddressResponse>) {
                    listener.onSuccess(apiResponse)
                }

                override fun onApiResponseFailure(apiFailure: ApiResponse<AddressResponse>) {
                    listener.onFailure(apiFailure)
                }

                override fun onDeviceOrApiFailure(apiFailureBecsInternetOrOtherIssue: ApiResponse<AddressResponse>) {
                    listener.onError(apiFailureBecsInternetOrOtherIssue)
                }
            })
    }
}