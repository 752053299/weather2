package com.example.guiwu.myweather.db;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by 乔冠 on 2017/12/10. 省的javaBean
 */

public class Province extends DataSupport {

    @SerializedName("dbId")
    private int id;

    @SerializedName("name")
    private String provienceName;

    @SerializedName("id")
    private int provienceNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvienceName() {
        return provienceName;
    }

    public void setProvienceName(String provienceName) {
        this.provienceName = provienceName;
    }

    public int getProvienceNum() {
        return provienceNum;
    }

    public void setProvienceNum(int provienceNum) {
        this.provienceNum = provienceNum;
    }
}
