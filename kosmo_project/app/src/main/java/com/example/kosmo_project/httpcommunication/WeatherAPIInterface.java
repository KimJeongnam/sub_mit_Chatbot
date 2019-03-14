package com.example.kosmo_project.httpcommunication;

import com.example.kosmo_project.vo.weather.WeatherData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface WeatherAPIInterface {
    @GET("data/2.5/forecast")
    Call<WeatherData> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appid);
}
