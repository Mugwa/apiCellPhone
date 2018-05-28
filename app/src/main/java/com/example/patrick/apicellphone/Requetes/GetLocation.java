package com.example.patrick.apicellphone.Requetes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLocation {

    @SerializedName("status")
    public String status;
    @SerializedName("balance")
    public Integer balance;
    @SerializedName("address")
    public Datum address;

    public class Datum {
        @SerializedName("suburb")
        public String suburb;
        @SerializedName("city")
        public String city;
        @SerializedName("county")
        public String county;
        @SerializedName("state")
        public String state;
        @SerializedName("postcode")
        public String postcode;
        @SerializedName("country")
        public String country;
        @SerializedName("country_code")
        public String country_code;
    }
}
