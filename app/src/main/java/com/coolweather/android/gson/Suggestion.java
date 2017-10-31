package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 12098 on 2017/10/30 0030.
 */

public class Suggestion {
    @SerializedName("air")
    public Airquality airquality;

    public class Airquality {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("comf")
    public Comfort comfort;

    public class Comfort {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("cw")
    public CarWash carWash;

    public class CarWash {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("drsg")
    public Dressindex dressindex;

    public class Dressindex {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("flu")
    public Coldindex coldindex;

    public class Coldindex {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    public Sport sport;
    public class Sport{
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("trav")
    public Tourindex tourindex;

    public class Tourindex {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }

    @SerializedName("uv")
    public Uvindex uvindex;

    public class Uvindex {
        @SerializedName("brf")
        public String brief;

        @SerializedName("txt")
        public String brieftxt;
    }
}
