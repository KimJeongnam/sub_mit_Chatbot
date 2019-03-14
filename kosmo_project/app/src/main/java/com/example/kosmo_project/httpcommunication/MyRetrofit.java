package com.example.kosmo_project.httpcommunication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    public static final String BASE_URL = "http://192.168.0.112:8090/";
    private static MyRetrofit instance = new MyRetrofit();

    private Retrofit retrofit;

    private MyRetrofit(){
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
