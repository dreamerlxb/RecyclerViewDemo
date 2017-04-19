package com.dreamerlxb.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamerlxb.recyclerviewdemo.adapter.LoadMoreAdapter;
import com.dreamerlxb.recyclerviewdemo.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.decoration.LoadMoreDividerItemDecoration;

import java.util.List;

public class LoadMoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        recyclerView = (RecyclerView) findViewById(R.id.load_more_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LoadMoreDividerItemDecoration(this));

        final NormalAdapter adapter = new NormalAdapter(this, MyData.getTestGridData());
        final LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(recyclerView, adapter);
        loadMoreAdapter.setLoadingMoreView(getLayoutInflater().inflate(R.layout.load_more_item, recyclerView, false));
        loadMoreAdapter.setLoadMoreNoDataView(getLayoutInflater().inflate(R.layout.load_more_no_data, recyclerView, false));
        loadMoreAdapter.setLoadMoreListener(new LoadMoreAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> strs = MyData.getTestData();
                        adapter.addItems(strs);
                        loadMoreAdapter.notifyItemRangeInserted(adapter.getItemCount()- strs.size(), strs.size());
                        loadMoreAdapter.loadMoreFinish(false);
                    }
                }, 2000);
            }
        });
        recyclerView.setAdapter(loadMoreAdapter);
    }
}
