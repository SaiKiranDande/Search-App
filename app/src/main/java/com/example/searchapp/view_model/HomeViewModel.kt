package com.example.airtelapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchapp.view.App
import com.example.searchapp.R
import com.example.searchapp.data.AddressResponse
import com.example.searchapp.listener.ApiResponseListener
import com.example.searchapp.listener.MessageEvent
import com.example.searchapp.model.HomeModel
import org.greenrobot.eventbus.EventBus
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException

class HomeViewModel : ViewModel() {

    private var homeModel: HomeModel = HomeModel()
    private lateinit var addressList: MutableLiveData<AddressResponse>

    fun getResponse(query: String, city: String): MutableLiveData<AddressResponse> {
        addressList = MutableLiveData<AddressResponse>()

        homeModel.getResponseData(query, city, object : ApiResponseListener {
            override fun onSuccess(response: AddressResponse?) {
                addressList.postValue(response)
            }

            override fun onError(message: String) {
                EventBus.getDefault().post(MessageEvent(message = message))
            }

            override fun onFailuer(throwable: Throwable) {
                when (throwable) {
                    is ConnectException -> EventBus.getDefault().post(
                        MessageEvent(
                            message = App.getAppContext().getString(
                                R.string.check_connection
                            )
                        )
                    )
                    is NoRouteToHostException -> EventBus.getDefault().post(
                        MessageEvent(
                            message = App.getAppContext().getString(
                                R.string.invalid_url
                            )
                        )
                    )
                    is UnknownHostException -> EventBus.getDefault().post(
                        MessageEvent(
                            message = App.getAppContext().getString(
                                R.string.check_connection
                            )
                        )
                    )
                    else -> EventBus.getDefault().post(
                        MessageEvent(
                            message = App.getAppContext().getString(
                                R.string.please_try_later
                            )
                        )
                    )

                }
            }

        })

        return addressList;
    }

}