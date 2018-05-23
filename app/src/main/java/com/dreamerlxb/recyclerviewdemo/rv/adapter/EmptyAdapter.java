package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by sxb on 2017/3/17.
 */

public class EmptyAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1000;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
