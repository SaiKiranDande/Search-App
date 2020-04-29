package com.example.searchapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("addressList")
    private List<AddressListItem> addressList;

    @SerializedName("autoCompleteRequestString")
    private String autoCompleteRequestString;

    public void setAddressList(List<AddressListItem> addressList) {
        this.addressList = addressList;
    }

    public List<AddressListItem> getAddressList() {
        return addressList;
    }

    public void setAutoCompleteRequestString(String autoCompleteRequestString) {
        this.autoCompleteRequestString = autoCompleteRequestString;
    }

    public String getAutoCompleteRequestString() {
        return autoCompleteRequestString;
    }
}