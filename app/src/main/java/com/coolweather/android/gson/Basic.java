package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("cnty")
    public String cntyName;
    @SerializedName("id")
    public String weatherId;
    @SerializedName("lat")
    public String longitude;
    @SerializedName("lon")
    public String latitude;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
