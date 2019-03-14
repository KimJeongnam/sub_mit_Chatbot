package com.example.kosmo_project.enums;

import java.util.Arrays;

public enum DayOfWeekType {
    SUN("일요일", -1),
    MON("월요일", 0),
    THE("화요일", 1),
    WED("수요일", 2),
    THU("목요일", 3),
    FRI("금요일", 4),
    SAT("토요일", 5),
    EMPTY("없음", -100);

    private String stringValue;
    private int intValue;

    DayOfWeekType(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public DayOfWeekType findByType(String str){
        if(str.contains("일요일"))
            return DayOfWeekType.SUN;
        if(str.contains("월요일"))
            return DayOfWeekType.MON;
        if(str.contains("화요일"))
            return DayOfWeekType.THE;
        if(str.contains("수요일"))
            return DayOfWeekType.WED;
        if(str.contains("목요일"))
            return DayOfWeekType.THU;
        if(str.contains("금요일"))
            return DayOfWeekType.FRI;
        if(str.contains("토요일"))
            return DayOfWeekType.SAT;
        else
            return DayOfWeekType.EMPTY;
    }


   /* public DayOfWeekType findByType(String str){
        return Arrays.stream(DayOfWeekType.values())
                .filter(dayOfWeekType -> dayOfWeekType.hasKeyword(dayOfWeekType.getStringValue()))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasKeyword(String str) {
        return str.contains(stringValue);
    }*/
}
