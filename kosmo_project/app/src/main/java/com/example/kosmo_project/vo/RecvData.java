package com.example.kosmo_project.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecvData<T> {
    private String message;
    private Integer status;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
