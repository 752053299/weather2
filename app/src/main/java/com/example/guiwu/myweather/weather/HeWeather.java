package com.example.guiwu.myweather.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 目前天气最外层类
 */
public class HeWeather {

    @SerializedName("HeWeather6")
    public List<WeatherNow> mWeathers;
}
