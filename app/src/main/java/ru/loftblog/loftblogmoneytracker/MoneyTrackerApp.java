package ru.loftblog.loftblogmoneytracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;

import ru.loftblog.loftblogmoneytracker.utils.TokenStorage;

public class MoneyTrackerApp extends Application implements TokenStorage {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    public static void setToken (Context context, String token) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public  static String getToken(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(TOKEN_KEY, "1");
    }

    public static void setGoogleToken (Context context, String token) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN_GOOGLE_KEY, token);
        editor.commit();
    }

    public  static String getGoogleToken(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(TOKEN_GOOGLE_KEY, "2");
    }
}