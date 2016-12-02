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
import android.widget.EditText;
import android.widget.Toast;

import com.example.dan.myapplication.adapter.APListAdapter;
import com.example.dan.myapplication.model.AccessPoint;
import com.example.dan.myapplication.util.Client;
import com.example.dan.myapplication.R;
import com.example.dan.myapplication.model.RPoint;
import com.example.dan.myapplication.util.Constants;
import com.example.dan.myapplication.util.JsonHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private EditText txtNome;
    private Button btnSalva;
    private BroadcastReceiver receiver;
    private RecyclerView list;
    private APListAdapter adapter;

    static final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

    private ProgressDialog progress;
    private WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        progress = new ProgressDialog(AddActivity.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        txtNome = (EditText) findViewById(R.id.txtNome);
        btnSalva = (Button) findViewById(R.id.btnSalva);
        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                wifi.startScan();
                registerReceiver(receiver, filter);
                progress.setTitle(R.string.scansione);
                progress.show();
            }
        });

        list = (RecyclerView) findViewById(R.id.add_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(layoutManager);
        adapter = new APListAdapter();
        list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (receiver == null)
            receiver = new AddActivity.ScanReceiver();
        //registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(receiver);
    }

    private class BackgroundTask extends AsyncTask<String, Integer, String> {
        Client client;
        RPoint rp;

        public BackgroundTask(RPoint rp) {
            this.rp = rp;
            client = new Client();
            progress.setTitle(R.string.invio);
        }

        @Override
        protected String doInBackground(String... nome) {
            String esito;
            if (rp.nome.equals("NOTFOUND"))
                return "Errore: AP non sufficienti.";


            String jsonrp = JsonHelper.serToJson(rp);
            boolean b = client.init(AddActivity.this);
            if (b) {
                try {
                    client.pout.println(Constants.ServerMessages.SALVA);
                    client.pout.println(jsonrp);
                    String serverResponse = client.bin.readLine();

                    if (serverResponse.equals(Constants.ServerMessages.OK))
                        esito = "RefPoint Aggiunto!";
                    else
                        esito= "Errore con il db";
                } catch (IOException ex) {
                    esito= "Errore di IO";
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
            Toast.makeText(AddActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public class ScanReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(receiver);
            RPoint rp = new RPoint();
            List<ScanResult> results = wifi.getScanResults();
            if (results.size() < 3) {
                rp.nome = "NOTFOUND";
            } else {
                rp.nome = txtNome.getText().toString();

                Log.i("ScanCompleteReceiver", "onReceive: risultati:" + results.size());
                for (ScanResult res : results) {
                    AccessPoint ap = new AccessPoint(res.SSID, res.BSSID, res.level);
                    rp.AP.add(ap);
                }
                Collections.sort(rp.AP);
                Log.i("ScanCompleteReceiver", "onReceive: " + JsonHelper.serToJson(rp));
                adapter.setList(rp.AP);
            }
            new BackgroundTask(rp).execute();
        }
    }

}