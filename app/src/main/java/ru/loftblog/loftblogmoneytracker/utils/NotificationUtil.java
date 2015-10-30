package ru.loftblog.loftblogmoneytracker.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import ru.loftblog.loftblogmoneytracker.R;
import ru.loftblog.loftblogmoneytracker.ui.activity.MainActivity_;

public class NotificationUtil {

    private static final int NOTIFICATION_ID = 666;

    public static void updateNotifications(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationsKey =
                context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationsKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        if (displayNotifications) {
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(context);
            //Create Intent to launch this Activity again if the notification is clicked.
            Intent i = new Intent(context, MainActivity_.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);

            // Sets the small icon for the ticker
            builder.setSmallIcon(R.mipmap.ic_launcher);
            // Sets the indicator
            builder.setLights(Color.GRAY, 300, 1500);
            // Sets vibration
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
            // Sets default sound
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            // Cancel the notification when clicked
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText =
                    context.getResources().getString(R.string.notification_message);
            Bitmap largeIcon =
                    BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            // Build the notification
            Notification notification = builder.build();
            // Use the NotificationManager to show the notification
            NotificationManager nm = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTIFICATION_ID, notification);
        }
    }
}