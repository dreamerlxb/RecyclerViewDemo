package com.dreamerlxb.recyclerviewdemo.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamerlxb.recyclerviewdemo.R;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String channelID = "chat";
    NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notifi);

        // 本地通知
        String title = "通知标题";
        String content = "通知内容";

        Intent intent = new Intent(this, com.dreamerlxb.recyclerviewdemo.rv.MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        setNm();

        //1.实例化一个通知，指定图标、概要、时间
        //2.指定通知的标题、内容和intent
        Notification n = new NotificationCompat.Builder(this, channelID)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setDefaults( Notification.DEFAULT_SOUND)
                .setContentIntent(pi)
                .setOngoing(false)
                .build();

        //4.发送通知
        nm.notify(1, n);
    }

    private void setNm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "chat_channel_name";

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            nm.createNotificationChannel(channel);
        }
    }
}
