package com.dreamerlxb.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamerlxb.recyclerviewdemo.adapter.ExpandableAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

public class ExpandableSectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExpandableAdapter expandableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        expandableAdapter = new ExpandableAdapter(this,
                MyData.getExpandableSectionData(),
                MyData.getExpandableSubItems());

        recyclerView = (RecyclerView) findViewById(R.id.test_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(expandableAdapter);
    }
}
