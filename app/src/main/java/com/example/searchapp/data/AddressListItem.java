package com.example.searchapp.data;

import com.google.gson.annotations.SerializedName;


public class AddressListItem {

    @SerializedName("city")
    private String city;

    @SerializedName("pinCode")
    private String pinCode;

    @SerializedName("addressString")
    private String addressString;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("id")
    private String id;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("label")
    private String label;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getAddressString() {
        return addressString;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}