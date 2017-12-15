package com.example.guiwu.myweather.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 乔冠 on 2017/12/15. 目前天气
 */

public class WeatherNow {

    private String status;
    private Basic basic;
    private Now now;
    private Update update;

    private class Now{

        @SerializedName("cond_txt")
        private String weatherCondation;

        @SerializedName("fl")
        private String feelTmp;

        @SerializedName("tmp")
        private String defaultTmp;

        @SerializedName("wind_dir")
        private String windDir;

        @SerializedName("wind_spd")
        private String windSpd;
    }

    private class Update{
        @SerializedName("loc")
        private String updateTime;
    }
}
