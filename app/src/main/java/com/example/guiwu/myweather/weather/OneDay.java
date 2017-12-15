package com.example.guiwu.myweather.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 乔冠 on 2017/12/15.  ，没有写完
 */

public class OneDay {

    private String date;
    @SerializedName("tmp_max")
    private String tmpMax;
    @SerializedName("tmp_min")
    private String tmpMin;

}
