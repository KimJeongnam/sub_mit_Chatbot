package com.example.kosmo_project.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StdScore {
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("semester")
    @Expose
    private String semester;
    @SerializedName("avgscore")
    @Expose
    private String avgscore;
    @SerializedName("lectureCount")
    @Expose
    private String lectureCount;
    @SerializedName("passScore")
    @Expose
    private String passScore;
    @SerializedName("grade")
    @Expose
    private String grade;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(String avgscore) {
        this.avgscore = avgscore;
    }

    public String getLectureCount() {
        return lectureCount;
    }

    public void setLectureCount(String lectureCount) {
        this.lectureCount = lectureCount;
    }

    public String getPassScore() {
        return passScore;
    }

    public void setPassScore(String passScore) {
        this.passScore = passScore;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
