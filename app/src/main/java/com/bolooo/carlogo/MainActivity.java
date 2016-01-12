package com.bolooo.carlogo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bolooo.carlogo.adapter.CarLogoAdapter;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String redirect_uri = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=16544&format=json&ie=utf-8&oe=utf-8&query=%E6%B1%BD%E8%BD%A6%E6%A0%87%E5%BF%97&apn=0&arn=400&co=result.item[key=]&cb=jQuery110207512711787130684_1452481972311&_=1452481972348";
    public static final String CarBrandList = "http://api.che300.com/service/getCarBrandList?token=101024dcd097136bdd9c6733da749c8a";
    public Map<String, ItemCar> logoMap;

    private SuperRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoMap = new HashMap<>();
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager =new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        getLogo();
    }

    void getLogo() {
        StringRequest request = new StringRequest(Request.Method.GET,
                redirect_uri,
                createLogoReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void getCarBrandList() {
        StringRequest request = new StringRequest(Request.Method.GET,
                CarBrandList, createBrandSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    private Response.Listener<String> createBrandSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                InsureData data = gson.fromJson(response, InsureData.class);
                Log.i(getLocalClassName(), data.brand_list.size() + "");
                for (InsureCar car : data.brand_list) {
                    if (logoMap.containsKey(car.brand_name)) {
                        car.brand_logo = logoMap.get(car.brand_name).normalpic;
                    }
                    Log.i(getLocalClassName(), car.toString());
                }
            }
        };
    }


    private Response.Listener<String> createLogoReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response.substring(response.indexOf("(") + 1, response.indexOf(")"));
                Log.i(getLocalClassName(), json);
                Gson gson = new Gson();
                ResponseData data = gson.fromJson(json, ResponseData.class);
                Log.i(getLocalClassName(), data.data.get(0).result.item.size() + "");
                for (ItemCar car : data.data.get(0).result.item) {
                    logoMap.put(car.attrquery, car);
                }

                CarLogoAdapter adapter = new CarLogoAdapter(MainActivity.this,data.data.get(0).result.item);
                adapter.setHasStableIds(true);
                recyclerView.setAdapter(adapter);
            }
        };
    }


    protected Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }

    public void downLoad(ArrayList<InsureCar> brand_list) {
        String saveDir = Environment.getExternalStorageDirectory() + "/LogoStudio/";
        new DownloadService( saveDir, brand_list, new DownloadService.DownloadStateListener() {

            @Override
            public void onFinish() {
                //图片下载成功后，实现您的代码
            }

            @Override
            public void onFailed() {
                //图片下载成功后，实现您的代码

            }
        }).startDownload();
    }

    public static boolean writeData(String fileName, String info) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录,2.2的时候为:/mnt/sdcart  2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
                File saveFile = new File(sdCardDir, fileName + ".json");
                Log.i("=====", saveFile.getAbsolutePath());
                FileOutputStream outStream = new FileOutputStream(saveFile);
                outStream.write(info.getBytes());

                outStream.close();
                return true;
            } catch (Exception e) {
            }
            return false;
        } else {
            return false;
        }
    }

}
