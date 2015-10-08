package ru.loftblog.loftblogmoneytracker.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by nixoid on 10/8/15.
 */
public class TrackerAuthenticatorService extends Service {

    private TrackerAuthenticator mTrackerAuthenticator;

    @Override
    public void onCreate() {
        mTrackerAuthenticator = new TrackerAuthenticator(this);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
