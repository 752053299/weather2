package com.example.guiwu.myweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guiwu.myweather.db.City;
import com.example.guiwu.myweather.db.Country;
import com.example.guiwu.myweather.db.Province;
import com.example.guiwu.myweather.util.DividerItemDecoration;
import com.example.guiwu.myweather.util.HttpUtil;
import com.example.guiwu.myweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 乔先森 on 2017/12/10. 选择地区的fragment
 */

public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    private ProgressDialog mProgressDialog;
    private TextView titleText;
    private Button mBcakButton;
    private RecyclerView mRecyclerView;
    private List<String> datalist = new ArrayList<>();
    private AreaAdapter recyclerViewAdapter;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        updateUI();

        queryProvinces();

        return view;
    }

    /**
     * rercyclerView的初始化
     */
    private void updateUI(){


        recyclerViewAdapter = new AreaAdapter(datalist);

        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        mBcakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLeavel == LEVEL_CITY){
                    queryProvinces();
                }else if (currentLeavel ==LEVEL_COUNTRY){
                    queryCities();
                }
            }
        });
    }

    private class AreaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mAreaName;


        public AreaHolder(View itemView) {
            super(itemView);

            mAreaName = (TextView) itemView.findViewById(R.id.area_name);
            itemView.setOnClickListener(this);
        }

        public void bind(String name){
            mAreaName.setText(name);

        }

        //每条记录的点击事件
        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: ");
            //判断应该显示的子页面
            if (currentLeavel == LEVEL_PROVINCE) {
                //设置当前选定的省份
                slectedProvince = mProvinceList.get(getLayoutPosition());
                //设置标题
                titleText.setText(slectedProvince.getProvienceName());
                queryCities();
            } else if (currentLeavel == LEVEL_CITY) {
                //设置当前选定的城市
                slectedCity = mCities.get(getLayoutPosition());
                //设置标题
                titleText.setText(slectedCity.getCityName());
                queryCountry();
            } else if (currentLeavel == LEVEL_COUNTRY){
                slectedCountry = mCountries.get(getLayoutPosition());
                Toast.makeText(getContext(),"你点击了：" + slectedCountry.getCountryName(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AreaAdapter extends RecyclerView.Adapter<AreaHolder>{

        private List<String> areaNames;

        public AreaAdapter(List<String> datalist){
            areaNames = datalist;
        }

        @Override
        public AreaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.city_list,parent,false);
            return new AreaHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AreaHolder holder, int position) {
            holder.bind(areaNames.get(position));
        }

        @Override
        public int getItemCount() {
            return areaNames.size();
        }
    }







    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */

    private void queryProvinces(){
        titleText.setText("中国");
        mBcakButton.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
   //     Log.i(TAG, "主线程id： " + Thread.currentThread().getId());

        if (mProvinceList.size() > 0){  //数据库有数据
            datalist.clear();
            for (Province province : mProvinceList){
                datalist.add(province.getProvienceName());
                recyclerViewAdapter.notifyDataSetChanged();
            }
            currentLeavel = LEVEL_PROVINCE;
        }else { //数据库没数据
            String address = "http://guolin.tech/api/china/";
            queryFromServer(address,"province");
        }
    }

    /**
     * 查询全国所有的市，规则同上
     */

    private void queryCities(){
        mBcakButton.setVisibility(View.VISIBLE);
        mCities = DataSupport.where("provinceId = ?",String.valueOf(slectedProvince.getDbId())).find(City.class);
        if (mCities.size() > 0){
            datalist.clear();
            for (City city : mCities){
                datalist.add(city.getCityName());
                recyclerViewAdapter.notifyDataSetChanged();
            }
            currentLeavel = LEVEL_CITY;
        }else {
            String address = "http://guolin.tech/api/china/" + slectedProvince.getProvienceNum() + "/";
            queryFromServer(address,"city");
        }
    }

    /**
     * 查询市内所有的县
     */
    private void queryCountry(){
        mBcakButton.setVisibility(View.VISIBLE);
        mCountries = DataSupport.where("cityId = ?",String.valueOf(slectedCity.getDbId())).find(Country.class);
        if (mCountries.size() > 0){
            datalist.clear();
            for (Country country : mCountries){
                datalist.add(country.getCountryName());
                recyclerViewAdapter.notifyDataSetChanged();
            }
            currentLeavel = LEVEL_COUNTRY;
        }else {
            String address = "http://guolin.tech/api/china/" + slectedProvince.getProvienceNum() + "/" + slectedCity.getCityNum() + "/";
            queryFromServer(address,"country");
        }
    }

    /**
     * 从服务器查询数据
     * @param address url地址
     * @param type 查询的级别
     */
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequset(address, new Callback()  {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)){
                  //  Log.i(TAG, "okhttp回调id： " + Thread.currentThread().getId());
                    result = Utility.handleProvinceResponse(responseText);
                }else if ("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,slectedProvince.getDbId());
                }else if ("country".equals(type)){
                    result = Utility.handleCityResponse(responseText,slectedCity.getDbId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                Log.i(TAG, "更新UIid： " + Thread.currentThread().getId());
                                queryProvinces();
                            }else if ("city".equals(type)){
                                queryCities();
                            }else if ("country".equals(type)){
                                queryCountry();
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 显示对话框方法
     */
    private void showProgressDialog(){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭对话框方法
     */
    private void closeProgressDialog(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }
}
