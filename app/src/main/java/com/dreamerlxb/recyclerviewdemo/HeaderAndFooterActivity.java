package com.dreamerlxb.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dreamerlxb.recyclerviewdemo.adapter.HeaderAndFooterAdapter;
import com.dreamerlxb.recyclerviewdemo.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.decoration.DividerGridItemDecoration;

public class HeaderAndFooterActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_and_footer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "RecyclerView Header and Footer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.header_footer_rv);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        // 自定义
        NormalAdapter adapter = new NormalAdapter(this, MyData.getTestGridData());
        final HeaderAndFooterAdapter headerAdapter = new HeaderAndFooterAdapter(this, adapter);
        headerAdapter.addHeader(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));
        headerAdapter.addHeader(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));

        headerAdapter.addFooter(this.getLayoutInflater().inflate(R.layout.recycler_header_item, null));
        recyclerView.setAdapter(headerAdapter);
    }
}
