package com.example.kosmo_project.service;

import com.example.kosmo_project.enums.MainType;
import com.example.kosmo_project.enums.SubType;

import java.util.HashMap;
import java.util.Map;


public class ChatBotService {

    public ChatBotService(String str, CallbackInterface callback){
        MainType mainType = MainType.EMPTY;

        mainType = mainType.findbyMainType(str);


        SubType subType = SubType.EMPTY;
        subType = subType.findByType(str);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("mainType", mainType);
        map.put("subType", subType);
        map.put("str", str);

        callback.Callback(map);
    }
}
