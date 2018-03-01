package com.example.siakunin.settingshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    LocationSettings locationSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSettings = new LocationSettings(this);
        locationSettings.addLocationSettingsListener(new OnLocationSettingsListener() {
            @Override
            public void onLocationSettingsGranted() {
                Log.i("TAG","Нужные настройки получены!");
            }

            @Override
            public void onLocationSettingsDenied() {
                Log.i("TAG","Нужные настройки не были получены!");
            }
        });
        locationSettings.checkCurrentLocationSettings();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationSettings.onActivityResult(requestCode,resultCode,data);

    }
}
