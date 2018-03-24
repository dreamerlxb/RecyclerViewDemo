package com.dreamerlxb.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamerlxb.recyclerviewdemo.adapters.MainAdapter;
import com.dreamerlxb.recyclerviewdemo.listener.OnItemClickListener;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 自定义
        MainAdapter adapter = new MainAdapter(this, MyData.getMainData());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, com.dreamerlxb.recyclerviewdemo.rv.MainActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent2 = new Intent(this, com.dreamerlxb.recyclerviewdemo.rxjava.MainActivity.class);
                startActivity(intent2);
                break;
            case 2:
                Intent intent3 = new Intent(this, com.dreamerlxb.recyclerviewdemo.lottie.MainActivity.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent4 = new Intent(this, com.dreamerlxb.recyclerviewdemo.notifications.MainActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
