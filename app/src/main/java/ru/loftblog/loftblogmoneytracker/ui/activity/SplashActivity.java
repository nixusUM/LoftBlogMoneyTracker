package ru.loftblog.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import ru.loftblog.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftblog.loftblogmoneytracker.R;

@EActivity(R.layout.splash_activity)
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @AfterViews
    void showSpalsh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MoneyTrackerApp.getToken(getApplicationContext()).equals("1")) {
                    Intent loginIntent = new Intent(SplashActivity.this, UserRegistration_.class);
                    startActivity(loginIntent);
                } else
                {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity_.class);
                    startActivity(mainIntent);
                }
                finish();}}, SPLASH_TIME_OUT);
          }
 }
