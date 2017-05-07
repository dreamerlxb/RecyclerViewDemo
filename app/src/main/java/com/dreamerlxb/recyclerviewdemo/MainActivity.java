package com.dreamerlxb.recyclerviewdemo;

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
