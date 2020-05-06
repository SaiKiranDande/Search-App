package com.example.searchapp.model

import java.io.Serializable

class ApiResponse<T> : Serializable {
    var responseCode: Int = 0
    var responseBody: T? = null
    var responseError: Throwable? = null
        private set

    constructor(responseCode: Int, responseBody: T?, exception: Throwable?) {
        this.responseCode = responseCode
        this.responseBody = responseBody
        this.responseError = exception
    }

    constructor(){

    }

    fun setError(exception: Throwable) {
        this.responseError = exception
    }
}
