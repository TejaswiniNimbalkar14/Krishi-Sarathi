package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    int id;

    @SerializedName("description")
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
