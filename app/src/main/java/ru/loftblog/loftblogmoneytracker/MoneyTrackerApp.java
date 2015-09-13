package ru.loftblog.loftblogmoneytracker;

import com.activeandroid.ActiveAndroid;

public class MoneyTrackerApp extends com.activeandroid.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
