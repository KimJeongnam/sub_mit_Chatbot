package com.example.kosmo_project.enums;

import java.util.Arrays;

public enum SubType {
    TODAY("오늘"),
    DAY_AFTER_TOMORROW("모레"),
    TOMORROW("내일"),
    TOWEEK("이번주"),
    LASTSEME("지난 학기"),
    EMPTY("없음");

    private String value;

    SubType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public SubType findByType(String name){
        return Arrays.stream(SubType.values())
                .filter(subType -> subType.hasKeyword(name))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasKeyword(String str){
        return str.contains(value);
    }
}
