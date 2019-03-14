package com.example.kosmo_project.vo.weather;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.example.kosmo_project.ChatActivity;
import com.example.kosmo_project.MainActivity;
import com.example.kosmo_project.enums.SubType;
import com.example.kosmo_project.enums.WeatherType;
import com.example.kosmo_project.listviewadapter.ListViewItem;
import com.example.kosmo_project.service.CallbackInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataHandler {
    private final String APPID = "89f210e26c1747a676379d7c69a70a99";
    private CallbackInterface callback;
    private int day;
    private Calendar cal;

    public WeatherDataHandler(CallbackInterface callback, int day) {
        this.callback = callback;
        this.day = day;

        if(this.day== SubType.EMPTY.ordinal())
            this.day = SubType.TODAY.ordinal();

        cal = Calendar.getInstance();

        MainActivity.weatherAPIInterface.getWeather(ChatActivity.lat, ChatActivity.lon, APPID).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.isSuccessful()) {
                    weatherDataPaser(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                t.printStackTrace();
                String message = "Weather API request Fail!!\n"+t.toString();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("message", message);

                callback.Callback(map);
            }
        });
    }

    private void weatherDataPaser(WeatherData weatherData){
        cal.setTime(new Date());
        if(day == 1) day = 2;
        else if(day == 2) day = 1;
        cal.add(Calendar.DATE, day);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat kodf = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

        String date = df.format(cal.getTime());
        String koDate = kodf.format(cal.getTime());

        java.util.List<com.example.kosmo_project.vo.weather.List> lists = weatherData.getList();

        java.util.List<com.example.kosmo_project.vo.weather.List> datas = new java.util.ArrayList<com.example.kosmo_project.vo.weather.List>();

        for(com.example.kosmo_project.vo.weather.List list: lists){
            if(list.getDtTxt().contains(date)){
                datas.add(list);
            }
        }

        String message = "현재 위치 : "+ ChatActivity.myLocation+"\n";
        message += "날짜 : "+ koDate+"\n";

        java.util.List<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

        for(com.example.kosmo_project.vo.weather.List list: datas){
            Weather weather = list.getWeather().get(0);
            Main main = list.getMain();
            double regulator = main.getTemp()-273;
            Date time = null;
            WeatherType weatherType = WeatherType.valueOfEng(weather.getDescription());
            try {
                time = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(list.getDtTxt());
            }catch(Exception e){
                e.printStackTrace();
            }

            ListViewItem item = new ListViewItem();
            item.setWeatherType(weatherType);
            item.setTime(tf.format(time.getTime()));
            item.setRegulator(String.format("%.2f", regulator));

            listViewItems.add(item);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("listViewItems", listViewItems);

        callback.Callback(map);
    }
}
