package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import ru.loftblog.loftblogmoneytracker.R;

@EActivity(R.layout.splash_activity)
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @AfterViews
     void showSpalsh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, UserRegistration_.class);
                startActivity(i);
                finish();}}, SPLASH_TIME_OUT);
          }
 }
