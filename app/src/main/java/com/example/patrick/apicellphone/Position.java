package com.example.patrick.apicellphone;

public class Position {
    private String city;
    private String code;
    private String state;
    private String country;
    private String latitude;
    private String longitude;

    public Position(String city, String code, String state, String country, String latitude, String longitude){
        this.city = city;
        this.code = code;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
     *@brief: fonction appele par defaut
     */
    public Position (String ... param) {
        String lattmp="46.989797", longtmp="6.929285";

        switch(param.length) {
            case 6:
                lattmp = param[4];
                longtmp = param[5];
                break;
            default:
                break;
        }
        new Position(param[0], param[1], param[2], param[3], lattmp, longtmp);
    }


    //Set + get
    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
