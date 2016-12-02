package com.example.dan.myapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dan.myapplication.R;
import com.example.dan.myapplication.ui.dialog.ServerSettingsDialog;
import com.example.dan.myapplication.util.PreferencesHelper;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, ServerSettingsDialog.ServerSettingsInterface {

    private LinearLayout serverSettings;
    private TextView settingsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverSettings = (LinearLayout) findViewById(R.id.serverSettings);
        settingsLabel = (TextView) findViewById(R.id.serverSettingsLabel);
        serverSettings.setOnClickListener(this);

        String ip = PreferencesHelper.readServerIp(this);
        int port = PreferencesHelper.readServerPort(this);
        setServerSettingsLabel(ip,port);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serverSettings:
                showServerSettingsDialog();
                break;
        }
    }

    private void showServerSettingsDialog() {
        new ServerSettingsDialog().show(getFragmentManager(),ServerSettingsDialog.TAG);
    }

    @Override
    public void onSettingsSaved(String ip, int port) {
        setServerSettingsLabel(ip,port);
    }

    private void setServerSettingsLabel(String ip, int port) {
        settingsLabel.setText(ip+":"+port);
    }
}
