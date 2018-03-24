package com.dreamerlxb.recyclerviewdemo.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dreamerlxb.recyclerviewdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notifi);

        // 本地通知
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String title = "通知标题";
        String content = "通知内容";

        Intent intent = new Intent(this, com.dreamerlxb.recyclerviewdemo.rv.MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        //1.实例化一个通知，指定图标、概要、时间
        //2.指定通知的标题、内容和intent
        Notification n = new Notification.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(content)
                .setWhen(5000)
                .setContentIntent(pi)
                .build();

        //3.指定声音
        n.defaults = Notification.DEFAULT_SOUND;

        //4.发送通知
        nm.notify(1, n);
    }
}
