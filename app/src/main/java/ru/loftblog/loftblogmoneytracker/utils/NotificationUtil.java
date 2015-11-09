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

        String soundNotificationKey =
                context.getString(R.string.sound_enable_notifications_key);
        boolean soundNotification = prefs.getBoolean(soundNotificationKey,
                Boolean.parseBoolean(context.getString(R.string.sound_enable_notifications_default)));

        String vibraNotificationKey =
                context.getString(R.string.vibra_enable_notifications_key);
        boolean vibraNotification = prefs.getBoolean(vibraNotificationKey,
                Boolean.parseBoolean(context.getString(R.string.vibra_enable_notifications_default)));

        String lightNotificationKey =
                context.getString(R.string.light_enable_notifications_key);
        boolean lightNotification = prefs.getBoolean(lightNotificationKey,
                Boolean.parseBoolean(context.getString(R.string.light_enable_notifications_default)));

        if (displayNotifications) {
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(context);
            Intent i = new Intent(context, MainActivity_.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            if (lightNotification)
                builder.setLights(Color.CYAN, 300, 1500);
            if (vibraNotification)
            builder.setVibrate(new long[] { 1000, 566, 1000, 444, 888 });
            if (soundNotification)
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
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

            Notification notification = builder.build();
            NotificationManager nm = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTIFICATION_ID, notification);
        }
    }
}