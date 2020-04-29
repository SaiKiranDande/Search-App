package com.example.searchapp.listener

import com.example.searchapp.data.AddressResponse


interface ApiResponseListener {

    fun onSuccess(response: AddressResponse?)
    fun onError(message: String)
    fun onFailuer(throwable: Throwable)
}