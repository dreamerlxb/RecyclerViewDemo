package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by sxb on 2017/3/15.
 */

public class NormalAdapter extends RecyclerView.Adapter {
    public static final int ITEM = 1;
    private Context context;

    private List<String> dataList;

    public NormalAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
