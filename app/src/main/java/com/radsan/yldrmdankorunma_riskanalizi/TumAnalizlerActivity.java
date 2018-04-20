package com.radsan.yldrmdankorunma_riskanalizi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import spencerstudios.com.bungeelib.Bungee;

public class TumAnalizlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tum_analizler);
    }

    @Override
    public void onBackPressed(){

        TumAnalizlerActivity.super.onBackPressed();
        Bungee.slideRight(TumAnalizlerActivity.this);
    }
}
