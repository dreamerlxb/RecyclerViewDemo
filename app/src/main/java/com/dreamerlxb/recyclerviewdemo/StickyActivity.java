package com.dreamerlxb.recyclerviewdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.adapter.SectionAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

public class StickyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View stickyView;

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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final SectionAdapter adapter = new SectionAdapter(this, MyData.getSectionGridData2());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                stickyHeaderHeight = stickyView.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                ((LinearLayoutManager)recyclerView.getLayoutManager()).
//                Log.i("Sticky", dx + "    " + dy);
                if (adapter.isSection(rvCurrentPos + 1)) {
                    View view = linearLayoutManager.findViewByPosition(rvCurrentPos + 1);
                    if (view != null) {
                        if(view.getTop() <= stickyHeaderHeight) {
                            stickyView.setY(-(stickyHeaderHeight - view.getTop()));
                        } else {
                            stickyView.setY(0);
                        }
                    }
                }


                if (rvCurrentPos != linearLayoutManager.findFirstVisibleItemPosition()) {
                    rvCurrentPos = linearLayoutManager.findFirstVisibleItemPosition();
                    stickyView.setY(0);
                }
            }
        });
    }

}
