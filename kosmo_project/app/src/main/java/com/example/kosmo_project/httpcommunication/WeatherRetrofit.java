package com.example.kosmo_project.httpcommunication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofit {
    public static final String BASE_URL = "http://api.openweathermap.org/";

    private static WeatherRetrofit instance = new WeatherRetrofit();

    private Retrofit retrofit;

    private WeatherRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit(){
        return instance.getthisRetrofit();
    }

    private Retrofit getthisRetrofit(){ return retrofit;}
}
