package com.example.dan.myapplication.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {
    //private static final String IP = "212.189.207.141";
    //private static final int PORT = 8899;
    private static final int TIMEOUT = 5000;
    public Socket socket;
    public BufferedReader bin;
    public PrintWriter pout;

    public boolean init(Context context) {
        String ip = PreferencesHelper.readServerIp(context);
        int port = PreferencesHelper.readServerPort(context);


        try {
            Log.i("AppLocClient", "initSocket: ");
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,port),TIMEOUT);
            Log.i("AppLocClient", "initBin: ");
            bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            return true;
        } catch (Exception e) {
            Log.i("AppLocClient", "exception: ");
            e.printStackTrace();
            return false;
        }
    }

    public void chiudi() {
        try {
            bin.close();
            pout.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
