package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Weather {
    public String status;

    public Basic basic;

    @SerializedName("daily_forecast")
    public List<Daily_forecast> daily_forecastList;

    @SerializedName("hourly_forecast")
    public List<Hourly_forecast> hourly_forecastList;

    public Now now;

    public Suggestion suggestion;

}
