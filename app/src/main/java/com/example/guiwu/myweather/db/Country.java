package com.example.guiwu.myweather.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by 乔冠 on 2017/12/10. 县的javaBean
 */

public class Country extends DataSupport {

    @SerializedName("dbId")
    private int id;

    @SerializedName("name")
    private String countryName;


    private String weather_Id;

    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeather_Id() {
        return weather_Id;
    }

    public void setWeather_Id(String weather_Id) {
        this.weather_Id = weather_Id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
