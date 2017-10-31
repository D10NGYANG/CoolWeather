package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Daily_forecast {
    public Astro astro;
    public class Astro{
        @SerializedName("mr")
        public String monrisetime;

        @SerializedName("ms")
        public String monfalltime;

        @SerializedName("sr")
        public String sunrisetime;

        @SerializedName("ss")
        public String sunsettime;
    }
    public Cond cond;
    public class Cond{
        @SerializedName("code_d")
        public String dayweathercode;

        @SerializedName("code_n")
        public String nightweathercode;

        @SerializedName("txt_d")
        public String dayweathertxt;

        @SerializedName("txt_n")
        public String nightweathertxt;
    }

    public String date;

    @SerializedName("hum")
    public String humidity;

    @SerializedName("pcpn")
    public String precipitation;

    @SerializedName("pop")
    public String pcpnprobability;

    @SerializedName("pres")
    public String pressure;

    public Tmp tmp;
    public class Tmp{
        @SerializedName("max")
        public String maxtem;

        @SerializedName("min")
        public String mintem;
    }

    @SerializedName("uv")
    public String uvindex;

    @SerializedName("vis")
    public String viskm;

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
