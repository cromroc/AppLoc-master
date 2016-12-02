package com.example.dan.myapplication.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dan.myapplication.R;
import com.example.dan.myapplication.adapter.APListAdapter;
import com.example.dan.myapplication.model.AccessPoint;
import com.example.dan.myapplication.model.RPoint;
import com.example.dan.myapplication.util.Client;
import com.example.dan.myapplication.util.Constants;
import com.example.dan.myapplication.util.JsonHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FindActivity extends AppCompatActivity {

    private Button btnLocal;
    private ProgressDialog progress;
    private static WifiManager wifi;
    private BroadcastReceiver receiver;
    private RecyclerView find_list;
    private APListAdapter adapter;
    private TextView risultato;

    static final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        
        progress=new ProgressDialog(this);
        progress.setTitle(R.string.scansione);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        risultato = (TextView) findViewById(R.id.aplist);

        wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);

        btnLocal=(Button)findViewById(R.id.btnLocalizza);
        
        btnLocal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                wifi.startScan();
                registerReceiver(receiver, filter);
                progress.setTitle(R.string.scansione);
                progress.show();
            }
        });

        find_list = (RecyclerView) findViewById(R.id.find_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        find_list.setLayoutManager(layoutManager);
        adapter = new APListAdapter();
        find_list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver == null)
            receiver = new ScanReceiver();
        //registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(receiver);
    }

    private class BackgroundTask extends AsyncTask<Void, Integer, String> {
        Client client;
        private RPoint rp;

        public BackgroundTask(RPoint rp) {
            this.rp = rp;
            client = new Client();
            progress.setTitle(R.string.invio);
        }

        @Override
        protected String doInBackground(Void... args) {
            String jsonrp = JsonHelper.serToJson(rp);
            String esito;
            boolean b = client.init(FindActivity.this);
            if (b){
                try{
                    client.pout.println(Constants.ServerMessages.TROVA);
                    client.pout.println(jsonrp);
                    esito = client.bin.readLine();
                } catch (IOException ex){
                    esito = "Errore di IO";
                }
            } else {
                esito = "Server OFF";
            }

            client.chiudi();
            return esito;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            risultato.setText(getString(R.string.risultato,result));
        }
    }


    public class ScanReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(receiver);
            RPoint rp = new RPoint();
            List<ScanResult> results = wifi.getScanResults();
            Log.i("ScanCompleteReceiver", "onReceive: risultati:"+results.size());
            for (ScanResult res : results) {
                AccessPoint ap = new AccessPoint(res.SSID,res.BSSID,res.level);
                rp.AP.add(ap);
            }
            Collections.sort(rp.AP);
            adapter.setList(rp.AP);
            Log.i("ScanCompleteReceiver", "onReceive: "+ JsonHelper.serToJson(rp));

            new BackgroundTask(rp).execute();
        }
    }

}
