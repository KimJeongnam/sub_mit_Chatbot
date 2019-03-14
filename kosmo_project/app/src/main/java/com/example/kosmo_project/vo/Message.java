package com.example.kosmo_project.vo;

import com.example.kosmo_project.listviewadapter.ListViewItem;

import java.util.List;

public class Message {
    public static final int VIEW_TYPE_MESSAGE_SENT = 1;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    public static  List<Message> messageList;

    private String message;
    String nickname;
    private String time;
    private int type;
    private java.util.List<ListViewItem> weathers;
    private List<StdScore> stdScoreList;
    private List<Lecture> lectures;
    private boolean isVoice;

    public boolean isVoice() {
        return isVoice;
    }

    public void setVoice(boolean voice) {
        isVoice = voice;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public java.util.List<ListViewItem> getWeathers() {
        return weathers;
    }

    public void setWeathers(java.util.List<ListViewItem> weathers) {
        this.weathers = weathers;
    }

    public List<StdScore> getStdScoreList() {
        return stdScoreList;
    }

    public void setStdScoreList(List<StdScore> stdScoreList) {
        this.stdScoreList = stdScoreList;
    }
}
