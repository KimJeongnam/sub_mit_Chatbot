package com.example.kosmo_project.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StdLecTime {
    @SerializedName("lecCode")
    @Expose
    private Integer lecCode;
    @SerializedName("lectureName")
    @Expose
    private String lectureName;
    @SerializedName("classRoom")
    @Expose
    private String classRoom;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("userNumber")
    @Expose
    private String userNumber;
    @SerializedName("timeTblcode")
    @Expose
    private Integer timeTblcode;
    @SerializedName("classTime")
    @Expose
    private Integer classTime;

    public Integer getLecCode() {
        return lecCode;
    }

    public void setLecCode(Integer lecCode) {
        this.lecCode = lecCode;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getTimeTblcode() {
        return timeTblcode;
    }

    public void setTimeTblcode(Integer timeTblcode) {
        this.timeTblcode = timeTblcode;
    }

    public Integer getClassTime() {
        return classTime;
    }

    public void setClassTime(Integer classTime) {
        this.classTime = classTime;
    }
}
