package com.example.guiwu.myweather.util;

import android.text.TextUtils;

import com.example.guiwu.myweather.db.City;
import com.example.guiwu.myweather.db.Country;
import com.example.guiwu.myweather.db.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 乔冠 on 2017/12/10. 解析服务器返回的省市县数据  http://guolin.tech/api/china
 */

public class Utility {

    /**
     * 解析省
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){

            Gson gson = new Gson();
            try {
                //将json字符串转换成Province集合
                Type listType = new TypeToken<ArrayList<Province>>(){}.getType();

                ArrayList<Province> provinces = gson.fromJson(response,listType);

                //将得到的集合逐个保存到数据库
                for (Province province : provinces){
                    province.save();
                }

               return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析市
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            try {

                Type listType = new TypeToken<ArrayList<City>>(){}.getType();

                ArrayList<City> cities = gson.fromJson(response,listType);

                for (City city: cities){
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 解析县
     */
    public static boolean handleCountryResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            Gson gson = new Gson();
            try {

                Type listType = new TypeToken<ArrayList<Country>>(){}.getType();

                ArrayList<Country> countries = gson.fromJson(response,listType);

                for (Country country: countries){
                    country.setCityId(cityId);
                    country.save();
                }

                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }
}
