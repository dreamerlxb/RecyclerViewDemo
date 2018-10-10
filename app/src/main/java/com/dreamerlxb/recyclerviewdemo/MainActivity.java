package com.dreamerlxb.recyclerviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.rv.listener.OnItemClickListener;
import com.dreamerlxb.recyclerviewdemo.data.MyData;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 自定义
        MainAdapter adapter = new MainAdapter(this, MyData.getMainData());
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, com.dreamerlxb.recyclerviewdemo.rv.MainActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent2 = new Intent(this, com.dreamerlxb.recyclerviewdemo.rxjava.MainActivity.class);
                startActivity(intent2);
                break;
            case 2:
                Intent intent3 = new Intent(this, com.dreamerlxb.recyclerviewdemo.lottie.MainActivity.class);
                startActivity(intent3);
                break;
            case 3:
                Intent intent4 = new Intent(this, com.dreamerlxb.recyclerviewdemo.notifications.MainActivity.class);
                startActivity(intent4);
                break;
            case 4:
                Intent intent5 = new Intent(this, com.dreamerlxb.recyclerviewdemo.editor.MainActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }

    public static class MainAdapter extends RecyclerView.Adapter implements View.OnClickListener {
        private static final int ITEM_TYPE_NORMAL = 1000;
        private Context context;

        private List<String> dataList;

        private OnItemClickListener itemClickListener;

        void setItemClickListener(OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        MainAdapter(Context context, List<String> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
            view.setOnClickListener(this);
            return new MainAdapter.MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            MainAdapter.MainViewHolder vh = (MainAdapter.MainViewHolder) holder;
            vh.textView.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return ITEM_TYPE_NORMAL;
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick((Integer) view.getTag());
            }
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            MainViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.main_rv_item_txt);
            }
        }
    }
}
