package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {
    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("weather")
    private List<Weather> weatherList;

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
