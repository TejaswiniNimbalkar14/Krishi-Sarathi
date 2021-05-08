package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    String wind;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
