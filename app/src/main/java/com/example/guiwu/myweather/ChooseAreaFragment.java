package com.example.guiwu.myweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guiwu.myweather.db.City;
import com.example.guiwu.myweather.db.Country;
import com.example.guiwu.myweather.db.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 乔先森 on 2017/12/10.
 */

public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    private ProgressDialog mProgressDialog;
    private TextView titleText;
    private Button mBcakButton;
    private RecyclerView mRecyclerView;
    private List<String> datalist = new ArrayList<>();

    /**
     * 省列表
     */
    private List<Province> mProvinceList;

    /**
     * 市列表
     */
    private List<City> mCities;

    /**
     * 县列表
     */
    private List<Country> mCountries;

    /**
     * 选中的省份
     */
    private Province slectedProvince;

    /**
     * 选中的市
     */
    private City slectedCity;

    /**
     * 选中的县
     */
    private Country slectedCountry;

    /**
     * 当前选中的级别
     */
    private int currentLeavel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        mBcakButton = (Button) view.findViewById(R.id.back_button);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }


    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */

    private void queryProvinces(){
        titleText.setText("中国");
        mBcakButton.setVisibility(View.GONE);
    }
}
