package com.dreamerlxb.recyclerviewdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamerlxb.recyclerviewdemo.adapter.MainAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

public class MainActivity extends AppCompatActivity implements MainAdapter.NormalItemClickListener {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 自定义
        MainAdapter adapter = new MainAdapter(this, MyData.getNormalData());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);


        // 本地通知
        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String title = "通知标题" ;
        String content = "通知内容" ;

        Intent intent = new Intent(this, HeaderAndFooterActivity.class);
        PendingIntent pi= PendingIntent.getActivity(this, 0, intent, 0);

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

    @Override
    public void onItemClickListener(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, HeaderAndFooterActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent2 = new Intent(this, SectionActivity.class);
                startActivity(intent2);
                break;
            case 2:
                Intent intent3 = new Intent(this, StickySectionActivity.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent4 = new Intent(this, StickySectionDecorActivity.class);
                startActivity(intent4);
                break;
            case 4:
                Intent intent5 = new Intent(this, LoadMoreActivity.class);
                startActivity(intent5);
                break;
            case 5:
                Intent intent6 = new Intent(this, ScaleHeaderActivity.class);
                startActivity(intent6);
                break;
            case 6:
                Intent intent7 = new Intent(this, TestActivity.class);
                startActivity(intent7);
                break;
            default:
                break;
        }
    }
}
