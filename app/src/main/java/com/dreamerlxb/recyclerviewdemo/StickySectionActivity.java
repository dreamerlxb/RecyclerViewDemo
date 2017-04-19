package com.dreamerlxb.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.adapter.StickySectionAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.entity.StickySectionEntityImpl;
import com.dreamerlxb.recyclerviewdemo.listener.RecyclerViewItemListener;

public class StickySectionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StickySectionAdapter stickySectionAdapter;
    private View stickyView;
    private TextView stickyTv;

    private int stickyHeaderHeight;
    private int rvCurrentPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Recycler View Sticky", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.sticky_rv);
        stickyView = findViewById(R.id.sticky_header);
        stickyTv = (TextView) stickyView.findViewById(R.id.sticky_section_item_txt);
        linearLayoutManager = new LinearLayoutManager(this);
        stickySectionAdapter = new StickySectionAdapter(this, MyData.getData2());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(stickySectionAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemListener(recyclerView) {
            @Override
            public void onClick(View view, int position) {
                super.onClick(view, position);
                Log.i("View", position + "");
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                stickyHeaderHeight = stickyView.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (stickySectionAdapter.isSection(rvCurrentPos + 1)) {
                    View view = linearLayoutManager.findViewByPosition(rvCurrentPos + 1);
                    if (view != null) {
                        if(view.getTop() < stickyHeaderHeight) {
                            stickyView.setY(-(stickyHeaderHeight - view.getTop()));
                        } else {
                            stickyView.setY(0);
                            updateStickyHeader(rvCurrentPos);
                        }
                    }
                }

                if (rvCurrentPos != linearLayoutManager.findFirstVisibleItemPosition()) {
                    rvCurrentPos = linearLayoutManager.findFirstVisibleItemPosition();
                    stickyView.setY(0);
                    updateStickyHeader(rvCurrentPos);
                }
            }
        });
    }

    private void updateStickyHeader(int pos) {
        int sec = stickySectionAdapter.getSectionForPosition(pos);
        Object se = stickySectionAdapter.getItemObject(sec);
        if (se != null) {
            StickySectionEntityImpl ssei = (StickySectionEntityImpl) se;
            stickyTv.setText(ssei.getTitle());
        }
    }

}
