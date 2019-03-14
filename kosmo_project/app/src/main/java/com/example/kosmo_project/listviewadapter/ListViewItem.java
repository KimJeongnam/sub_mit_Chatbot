package com.example.kosmo_project.listviewadapter;

import com.example.kosmo_project.enums.WeatherType;

public class ListViewItem {
    private WeatherType weatherType;    // 날씨 타입
    private String time;        // 시간
    private String regulator;   //섭씨

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }
}
