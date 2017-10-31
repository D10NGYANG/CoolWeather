package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Now {
    public Cond cond;
    public class Cond{
        @SerializedName("code")
        public String weathercode;

        @SerializedName("txt")
        public String weathertxt;
    }

    @SerializedName("f1")
    public String sendibletem;

    @SerializedName("hum")
    public String humidity;

    @SerializedName("pcpn")
    public String precipitation;

    @SerializedName("pres")
    public String pressure;

    @SerializedName("tmp")
    public String temperature;

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
