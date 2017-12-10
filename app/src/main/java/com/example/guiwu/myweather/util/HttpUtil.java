package com.example.guiwu.myweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 乔冠 on 2017/12/10. okhttp
 */

public class HttpUtil {

    public static void sendOkHttpRequset(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }
}
