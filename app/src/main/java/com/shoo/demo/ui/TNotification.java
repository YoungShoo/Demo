package com.shoo.demo.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.shoo.demo.R;

/**
 * Created by Shoo on 17-9-22.
 */

public class TNotification {

    public static void test(Activity activity) {
        Notification.Builder builder = new Notification.Builder(activity);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("title");
        builder.setContentText("content");
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context
                .NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        Notification.Builder builder2 = new Notification.Builder(activity);
        builder2.setSmallIcon(R.drawable.ic_launcher);
        builder2.setContentTitle("title2");
        builder2.setContentText("content2");
        builder2.setPriority(Notification.PRIORITY_MAX);
        Notification notification2 = builder2.build();

        notificationManager.notify(2, notification2);
    }

}
