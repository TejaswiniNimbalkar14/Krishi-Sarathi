package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?appid=4e9716671e98e07336792b157fb83022&units=metric")
    Call<Example> getWeatherData(@Query("q") String name);

}
