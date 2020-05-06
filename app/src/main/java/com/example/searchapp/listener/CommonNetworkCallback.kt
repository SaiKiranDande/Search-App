package com.example.searchapp.listener

import com.example.searchapp.model.ApiResponse
import com.example.searchapp.utils.isAPISuccess
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType

abstract class CommonNetworkCallback<T> : Callback<T> {

    private val call: Call<T>?
    private var totalRetries: Int = 0
    private var retryCount = 0

    constructor() {
        this.call = null
        this.totalRetries = 0
    }

    constructor(call: Call<T>, totalRetries: Int) {
        this.call = call
        this.totalRetries = totalRetries
    }

    constructor(call: Call<T>) {
        this.call = call
        this.totalRetries = 3
    }


    override fun onFailure(call: Call<T>, t: Throwable) {
        if (this.call != null && retryCount++ < totalRetries) {
            retry()
        } else {
            sendErrorInformationToRootRequest(call, t)
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val responseCode = response.code()
        if (responseCode.isAPISuccess()) {
            onApiResponseSuccess(ApiResponse<T>(response.code(), response.body(), null))
        } else if (this.call != null && retryCount++ < totalRetries) {
            retry()
        } else {
            otherServerErrorHandler(call, response)
        }
    }

    protected fun otherServerErrorHandler(call: Call<T>, response: Response<T>) {
        val gson = GsonBuilder().create()
        var serverErrorResponse: T? = null
        var errorApiResponse: ApiResponse<T>? = null
        try {
            serverErrorResponse = gson.fromJson(
                response.errorBody()!!.string(),
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
            )
        } catch (e: IOException) {
            errorApiResponse = ApiResponse()
            /*if (e is NoConnectivityException) {
                errorApiResponse!!.setResponseCode((e as NoConnectivityException).getErrorCode())
            } else
                errorApiResponse!!.setResponseCode(getStatusCodeFromResponse(response))*/
            errorApiResponse.setError(e)
        } catch (e: Exception) {
            errorApiResponse = ApiResponse()
//            errorApiResponse!!.setResponseCode(getStatusCodeFromResponse(response))
            errorApiResponse.setError(e)
        }

        if (serverErrorResponse != null) {
            onApiResponseFailure(
                ApiResponse(
                    getStatusCodeFromResponse(response),
                    serverErrorResponse,
                    null
                )
            )
        } else {
            if (errorApiResponse == null) {
                errorApiResponse = ApiResponse()
//                errorApiResponse!!.setResponseCode(ServerResponseCode.SOME_THING_WENT_WRONG)
            }
            onDeviceOrApiFailure(errorApiResponse)
        }
    }

    private fun retry() {
        call?.clone()?.enqueue(this)
    }

    protected fun sendErrorInformationToRootRequest(call: Call<T>, t: Throwable) {
        call.cancel()
        val errorResponse = ApiResponse<T>()
        /*if (t is NoConnectivityException) {
            errorResponse.setResponseCode((t as NoConnectivityException).getErrorCode())
        } else
            errorResponse.setResponseCode(ServerResponseCode.SOME_THING_WENT_WRONG)*/
        errorResponse.setError(t)
        onDeviceOrApiFailure(errorResponse)
    }

    private fun getStatusCodeFromResponse(response: Response<T>?): Int {
        var statusCode = 0
        if (response != null) {
            statusCode = response.code()
        }
        return statusCode
    }

    abstract fun onApiResponseSuccess(apiResponse: ApiResponse<T>)

    abstract fun onApiResponseFailure(apiFailure: ApiResponse<T>)

    abstract fun onDeviceOrApiFailure(apiFailureBecsInternetOrOtherIssue: ApiResponse<T>)
}