package com.dreamerlxb.recyclerviewdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dreamerlxb.recyclerviewdemo.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.adapter.HeaderAndFooterAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;
import com.dreamerlxb.recyclerviewdemo.divider.DividerGridItemDecoration;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextMessage;
    private RecyclerView recyclerView;
    int total = 10;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_me:
//                    mTextMessage.setText(R.string.title_me);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 自定义
        NormalAdapter adapter = new NormalAdapter(this, MyData.getSampleData());
        final HeaderAndFooterAdapter headerAdapter = new HeaderAndFooterAdapter(this, adapter);
        recyclerView.setAdapter(headerAdapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (headerAdapter.getItemViewType(position) == HeaderAndFooterAdapter.ITEM_HEADER) {
                    return manager.getSpanCount();
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }

}
