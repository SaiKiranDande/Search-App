package com.example.airtelapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {


    private fun setRetrofit(url: String) {

        val httpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(url).build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    companion object {
        var apiClient: ApiClient? = null
        lateinit var apiInterface: ApiInterface

        fun getInstance(): ApiClient {
            if (apiClient == null) {

                apiClient = ApiClient()
            }
            return apiClient!!
        }

        fun getApi(url: String): ApiInterface {
            getInstance().setRetrofit(url)
            return apiInterface
        }
    }

}