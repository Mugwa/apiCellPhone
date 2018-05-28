package com.example.patrick.apicellphone;

import com.example.patrick.apicellphone.Requetes.GetLocation;
import com.example.patrick.apicellphone.Requetes.GetPosition;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface APIInterface {
    @GET("/v2/reverse.php?token=91e76e2c7049f2&lat=46.9972421&lon=6.9377013")
    Call<GetLocation> doSth();

    @GET("/v2/search.php?token=91e76e2c7049f2&")
    //Call<GetPosition> getPos();
    Call<GetPosition> getPos(@Query("q") String query);
}
