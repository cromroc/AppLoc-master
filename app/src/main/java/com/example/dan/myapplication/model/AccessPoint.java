package com.example.dan.myapplication.model;

import android.support.annotation.NonNull;

/**
 * Created by DAN on 05/10/2016.
 */

public class AccessPoint implements Comparable<AccessPoint> {
    public String SSID;
    public String MAC;
    public int level;

    @Override
    public int compareTo( AccessPoint app) {
        if (level < app.level)
            return 1;
        else if (level == app.level)
            return 0;
        else
            return -1;
    }

    public AccessPoint(String SSID, String MAC, int level) {
        this.SSID = SSID;
        this.MAC = MAC;
        this.level = level;
    }

    public AccessPoint(){}
}
