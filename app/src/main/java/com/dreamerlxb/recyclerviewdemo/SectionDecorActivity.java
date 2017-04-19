package com.dreamerlxb.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dreamerlxb.recyclerviewdemo.adapter.SectionDecorAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.decoration.StickyItemDecoration;

public class SectionDecorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SectionDecorAdapter sectionDecorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_decoration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.section_deco_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        sectionDecorAdapter = new SectionDecorAdapter(this, MyData.getData3());
        recyclerView.setAdapter(sectionDecorAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

//        SectionItemDecoration sectionItemDecoration = new SectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.sectioned_top), new SectionItemDecoration.SectionItemDecorationCb() {
//            @Override
//            public int getSectionId(int position) {
//                return sectionDecorAdapter.getSectionForPosition(position);
//            }
//        });
//        recyclerView.addItemDecoration(sectionItemDecoration);

        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(getResources().getDimensionPixelSize(R.dimen.sectioned_top), new StickyItemDecoration.StickyItemDecorationCb() {
            @Override
            public int getSectionId(int position) {
                return sectionDecorAdapter.getSectionForPosition(position);
            }
        });
        recyclerView.addItemDecoration(stickyItemDecoration);
    }

}
