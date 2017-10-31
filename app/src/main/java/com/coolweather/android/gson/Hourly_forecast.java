package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Hourly_forecast {

    public Cond cond;
    public class Cond{
        @SerializedName("code")
        public String weathercode;

        @SerializedName("txt")
        public String weathertxt;
    }

    @SerializedName("date")
    public String dateTime;

    @SerializedName("hum")
    public String humidity;

    @SerializedName("pop")
    public String pcpnprobability;

    @SerializedName("pres")
    public String pressure;

    @SerializedName("tmp")
    public String temperature;

    public Wind wind;
    public class Wind{
        @SerializedName("deg")
        public String winddeg;

        @SerializedName("dir")
        public String winddir;

        @SerializedName("sc")
        public String windsc;

        @SerializedName("spd")
        public String windspd;
    }
}
