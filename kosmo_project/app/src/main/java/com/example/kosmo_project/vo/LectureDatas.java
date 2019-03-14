package com.example.kosmo_project.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LectureDatas {
    @SerializedName("lectures")
    @Expose
    private List<Lecture> lectures = null;
    @SerializedName("dayofweek")
    @Expose
    private String dayofweek;

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }
}
