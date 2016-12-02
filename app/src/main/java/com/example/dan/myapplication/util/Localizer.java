package com.example.dan.myapplication.util;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.example.dan.myapplication.model.RPoint;
import com.example.dan.myapplication.model.AccessPoint;

import java.util.List;

/**
 * Created by DAN on 05/10/2016.
 */

public class Localizer  {
    public WifiManager wifi;
    public String wifis[];
    public Context context;

    public Localizer(Context context) {
        this.context = context;
    }

    //WifiScanReceiver wifiReciever;

    public RPoint newPoint (String nome) {
        RPoint rp = new RPoint ();
        AccessPoint ap = new AccessPoint ();
        wifi=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        //wifiReciever = new WifiScanReceiver();
        wifi.startScan();
        List<ScanResult> wifiScanList = wifi.getScanResults();
        if (wifiScanList.size() < 3) {
            rp.nome = "NOTFOUND";
            return rp;
        }
        for(int i = 0; i < wifiScanList.size(); i++){
            ap.SSID = wifiScanList.get(i).SSID;
            ap.MAC = wifiScanList.get(i).BSSID;
            rp.AP.add(ap);
        }
        rp.nome = nome;
        return rp;
    }
}

