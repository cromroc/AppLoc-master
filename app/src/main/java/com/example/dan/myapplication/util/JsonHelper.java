package com.example.dan.myapplication.util;

import com.google.gson.Gson;

public class JsonHelper {

    public static String serToJson(Object obj) {
        return new Gson().toJson(obj);
    }

    public static <T> T deserFromJson(String jsonString, Class<T> T) {
        return new Gson().fromJson(jsonString, T);
    }
}