package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import com.google.gson.annotations.SerializedName;

public class Example {
    @SerializedName("main")
    private Main main;
    private Wind wind;

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

}
