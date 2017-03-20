package com.dreamerlxb.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.dreamerlxb.recyclerviewdemo.adapter.HeaderAndFooterAdapter;
import com.dreamerlxb.recyclerviewdemo.adapter.LoadMoreAdapter;
import com.dreamerlxb.recyclerviewdemo.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.decoration.DividerGridItemDecoration;
import com.dreamerlxb.recyclerviewdemo.decoration.LoadMoreDividerItemDecoration;

import java.util.List;

public class LoadMoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        recyclerView = (RecyclerView) findViewById(R.id.load_more_rv);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LoadMoreDividerItemDecoration(this));
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        final NormalAdapter adapter = new NormalAdapter(this, MyData.getTestGridData());
        final LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(this, adapter);
        loadMoreAdapter.setLoadMoreView(getLayoutInflater().inflate(R.layout.load_more_item, recyclerView, false));
        recyclerView.setAdapter(loadMoreAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    Log.i("1", "SCROLL_STATE_DRAGGING");
//                } else if(newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.i("2", "SCROLL_STATE_IDLE");
//                } else if(newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                    Log.i("3", "SCROLL_STATE_SETTLING");
//                }
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1) ) { //判断垂直方向上可否向上移动
                        // 在这加载更多数据 (数据已经加载完成)
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<String> strs = MyData.getTestData();
                                adapter.addItems(strs);
                                loadMoreAdapter.notifyItemRangeInserted(adapter.getItemCount()- strs.size(), strs.size());
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
