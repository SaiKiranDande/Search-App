package com.example.searchapp.data;

import com.google.gson.annotations.SerializedName;


public class AddressResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("requestId")
    private String requestId;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }
}