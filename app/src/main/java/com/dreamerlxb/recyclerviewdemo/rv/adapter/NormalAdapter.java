package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by sxb on 2017/3/15.
 */

public class NormalAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_NORMAL = 1000;
    private Context context;

    private List<String> dataList;

    public NormalAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void addItems(List<String> list) {
        dataList.addAll(list);
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("====", "cvh");
        View view = LayoutInflater.from(context).inflate(R.layout.normal_item, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        NormalViewHolder vh = (NormalViewHolder) holder;
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


    public class NormalViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_item_txt);
        }
    }
}
