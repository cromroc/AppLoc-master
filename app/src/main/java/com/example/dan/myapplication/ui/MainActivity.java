package com.example.dan.myapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dan.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAggiungiRP,btnTrova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAggiungiRP = (Button)findViewById(R.id.btnAggiungiRP);
        btnTrova=(Button)findViewById(R.id.btnTrova);

        btnAggiungiRP.setOnClickListener(this);
        btnTrova.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAggiungiRP:
                Intent openPage1 = new Intent(MainActivity.this,AddActivity.class);
                startActivity(openPage1);
                break;
            case R.id.btnTrova:
                Intent openPage2 = new Intent(MainActivity.this,FindActivity.class);
                startActivity(openPage2);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_impostazioni:
                goToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToSettings() {
        startActivity(new Intent(this,SettingsActivity.class));
    }
}
