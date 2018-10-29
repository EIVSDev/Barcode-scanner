package com.dev.eivs.testscan.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dev.eivs.testscan.MainActivity;
import com.dev.eivs.testscan.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config =new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#074E72"))
                .withLogo(R.drawable.ic_stat_name)
                .withHeaderText("Welome")
                .withFooterText("Copyright 2018")
                .withBeforeLogoText("EIVS Dev Co,Ltd")
                .withAfterLogoText("Barcode scaner");

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);

        View view = config.create();
        setContentView(view);
    }
}
