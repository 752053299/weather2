package com.example.guiwu.myweather.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 乔冠 on 2017/12/15.
 */

public class Basic {

    @SerializedName("location")
    private String cityName;

    @SerializedName("cid")
    private String weatherId;

}
