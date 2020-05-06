package com.example.airtelapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchapp.listener.ApiResponseListener
import com.example.searchapp.model.ApiResponse
import com.example.searchapp.model.HomeModel

class HomeViewModel : ViewModel() {

    private var homeModel: HomeModel = HomeModel()
    private lateinit var addressList: MutableLiveData<ApiResponse<*>>

    fun getResponse(query: String, city: String): MutableLiveData<ApiResponse<*>> {
        addressList = MutableLiveData<ApiResponse<*>>()

        homeModel.getResponseData(query, city, object : ApiResponseListener {
            override fun onSuccess(apiResponse: ApiResponse<*>) {
                addressList.postValue(apiResponse)
            }

            override fun onFailure(apiResponseFail: ApiResponse<*>) {
                addressList.postValue(apiResponseFail)
            }

            override fun onError(apiResponseError: ApiResponse<*>) {
                addressList.postValue(apiResponseError)
            }
        })
        return addressList;
    }

}