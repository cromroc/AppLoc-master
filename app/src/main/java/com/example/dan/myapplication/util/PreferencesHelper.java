package com.example.dan.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DAN on 26/11/2016.
 */

public class PreferencesHelper {

    public static void saveServerIp(Context context, String ip) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.Prefs.SERVER_IP,ip);
        editor.apply();
    }
    public static void deleteServerIp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
    }
    public static String readServerIp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.Prefs.SERVER_IP, "127.0.0.1"); //default localhost
    }

    public static void saveServerPort(Context context, int port) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.Prefs.SERVER_PORT,port);
        editor.apply();
    }
    public static void deleteServerPort(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
    }
    public static int readServerPort(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.Prefs.SERVER_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(Constants.Prefs.SERVER_PORT, 80); //Default 80
    }
}
