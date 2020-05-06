package com.example.searchapp.listener

import com.example.searchapp.model.ApiResponse


interface ApiResponseListener {
    fun onSuccess(apiResponse: ApiResponse<*>)
    fun onFailure(apiResponseFail: ApiResponse<*>)
    fun onError(apiResponseError: ApiResponse<*>)
}