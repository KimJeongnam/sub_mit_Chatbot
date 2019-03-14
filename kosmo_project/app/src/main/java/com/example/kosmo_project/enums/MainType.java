package com.example.kosmo_project.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum MainType {
    REPORT(Arrays.asList("과제", "레포트")),
    WEATHER(Arrays.asList("날씨")),
    TIMETABLE(Arrays.asList("시간표")),
    SCORE(Arrays.asList("학점", "성적")),
    INTRODUCTION(Arrays.asList("소개", "인사해", "인사", "넌 누구니", "넌누구니", "자기소개 해봐", "자기소개")),
    START_PROJECT(Arrays.asList("프로젝트 시작하자", "발표 시작해", "발표 시작", "프로젝트 시작", "시작")),
    END_PROJECT(Arrays.asList("프로젝트 종료", "발표 종료", "프로젝트를 종료", "종료")),
    RAPSTAR(Arrays.asList("랩 해 봐", "랩 해봐", "랩 해줘", "랩 해 줘", "랩 듣고 싶어", "너 랩좀하니?", "랩")),
    HELLO(Arrays.asList("안녕", "애나")),
    EMPTY(Collections.EMPTY_LIST);

    private List<String> listValue;

    MainType(List<String> listValue) {
        this.listValue = listValue;
    }

    public static MainType findbyMainType(String mainKeyword){
        return Arrays.stream(MainType.values())
                .filter(mainType -> mainType.hasKeyword(mainKeyword))
                .findAny()
                .orElse(EMPTY);
    }

    private boolean hasKeyword(String mainKeyword){
        return listValue.stream()
                .anyMatch(keyword->mainKeyword.contains(keyword));
    }
}