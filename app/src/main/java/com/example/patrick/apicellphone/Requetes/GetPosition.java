package com.example.patrick.apicellphone.Requetes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPosition {

    @SerializedName("status")
    public String status;
    @SerializedName("balance")
    public Integer balance;

    @SerializedName("address")
    public ArrayList<indexAddress> address=null;


    public class indexAddress {
        @SerializedName("city")
        public String city;
        @SerializedName("postcode")
        public String code;
        @SerializedName("state")
        public String state;
        @SerializedName("country")
        public String country;
        @SerializedName("lat")
        public String latitude;
        @SerializedName("lon")
        public String longiture;
    }
/*
    public class infoAddress {
        @SerializedName("county")
        public String county;
        @SerializedName("lat")
        public String latitude;
        @SerializedName("lon")
        public String longiture;
    }*/
}
