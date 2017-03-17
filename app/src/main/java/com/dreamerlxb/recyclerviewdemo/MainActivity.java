package com.dreamerlxb.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dreamerlxb.recyclerviewdemo.adapter.NormalAdapter;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

public class MainActivity extends AppCompatActivity implements NormalAdapter.NormalItemClickListener {

    private RecyclerView recyclerView;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
////                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
////                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
////                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//                case R.id.navigation_me:
////                    mTextMessage.setText(R.string.title_me);
//                    return true;
//            }
//            return false;
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 自定义
        NormalAdapter adapter = new NormalAdapter(this, MyData.getNormalData());
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
            default:
                break;
        }
    }
}
