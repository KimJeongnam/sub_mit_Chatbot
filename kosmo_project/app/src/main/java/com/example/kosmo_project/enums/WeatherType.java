package com.example.kosmo_project.enums;


import com.example.kosmo_project.R;

public enum WeatherType {
    /*
    sky is clear
    , few clouds
    , scattered clouds
    , broken clouds
    , overcast clouds
    , shower rain
    , light rain
    , moderate rain
    , Rain
    , Thunderstorm
    , snow
    , mist
    */
    SKY_IS_CLEAR("매우 맑음", "sky is clear", R.drawable.sky_is_clear),
    FEW_CLOUDS("약간의 구름", "few clouds", R.drawable.few_clouds),
    SCATTERED_CLOUDS("적은 구름", "scattered clouds", R.drawable.few_clouds),
    BROKEN_CLOUDS("구름 약간낌", "broken clouds", R.drawable.broken_clouds),
    OVERCAST_CLOUDS("구름 많음", "overcast clouds", R.drawable.overcast_clouds),
    SHOWER_RAIN("소나기", "shower rain", R.drawable.shower_rain),
    LIGHT_RAIN("가벼운 비", "light rain", R.drawable.light_rain),
    MODERATE_RAIN("적당한 비", "moderate rain", R.drawable.light_rain),
    RAIN("비", "Rain", R.drawable.rain),
    THUNDERSTORM("천둥 번개", "Thunderstorm", R.drawable.thunderstorm),
    SNOW("눈", "snow", R.drawable.snow),
    MIST("안개", "mist", R.drawable.mist);

    private String koValue;
    private String engValue;
    private int imageId;

    WeatherType(String koValue, String engValue, int imageId) {
        this.koValue = koValue;
        this.engValue = engValue;
        this.imageId = imageId;
    }

    public String getKoValue() {
        return koValue;
    }

    public String getEngValue() {
        return engValue;
    }

    public int getImageId() {
        return imageId;
    }

    public static WeatherType valueOfEng(String engValue){
        WeatherType type = WeatherType.SKY_IS_CLEAR;
        switch(engValue){
            case "sky is clear":
                type = WeatherType.SKY_IS_CLEAR;
                break;
            case "few clouds":
                type = WeatherType.FEW_CLOUDS;
                break;
            case "scattered clouds":
                type = WeatherType.SCATTERED_CLOUDS;
                break;
            case "broken clouds":
                type = WeatherType.BROKEN_CLOUDS;
                break;
            case "overcast clouds":
                type = WeatherType.OVERCAST_CLOUDS;
                break;
            case "shower rain":
                type = WeatherType.SHOWER_RAIN;
                break;
            case "light rain":
                type = WeatherType.LIGHT_RAIN;
                break;
            case "moderate rain":
                type = WeatherType.MODERATE_RAIN;
                break;
            case "Rain":
                type = WeatherType.RAIN;
                break;
            case "Thunderstorm":
                type = WeatherType.THUNDERSTORM;
                break;
            case "snow":
                type = WeatherType.SNOW;
                break;
            case "mist":
                type = WeatherType.MIST;
                break;
        }
        return type;
    }

    public static WeatherType valueOfKo(String koValue){
        WeatherType type = WeatherType.SKY_IS_CLEAR;
        switch(koValue){
            case "매우 맑음":
                type = WeatherType.SKY_IS_CLEAR;
                break;
            case "약간의 구름":
                type = WeatherType.FEW_CLOUDS;
                break;
            case "적은 구름":
                type = WeatherType.SCATTERED_CLOUDS;
                break;
            case "구름 약간낌":
                type = WeatherType.BROKEN_CLOUDS;
                break;
            case "구름 많음":
                type = WeatherType.OVERCAST_CLOUDS;
                break;
            case "소나기":
                type = WeatherType.SHOWER_RAIN;
                break;
            case "가벼운 비":
                type = WeatherType.LIGHT_RAIN;
                break;
            case "적당한 비":
                type = WeatherType.MODERATE_RAIN;
                break;
            case "비":
                type = WeatherType.RAIN;
                break;
            case "천둥 번개":
                type = WeatherType.THUNDERSTORM;
                break;
            case "눈":
                type = WeatherType.SNOW;
                break;
            case "안개":
                type = WeatherType.MIST;
                break;
        }
        return type;
    }
}
