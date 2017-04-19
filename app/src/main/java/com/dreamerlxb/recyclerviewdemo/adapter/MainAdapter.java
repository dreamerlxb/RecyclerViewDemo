package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by sxb on 2017/3/15.
 */

public class MainAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    public static final int ITEM_TYPE_NORMAL = 1000;
    private Context context;

    private List<String> dataList;

    private NormalItemClickListener itemClickListener;

    public void setItemClickListener(NormalItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface NormalItemClickListener {
        void onItemClickListener(int position);
    }

    public MainAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        view.setOnClickListener(this);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        MainViewHolder vh = (MainViewHolder) holder;
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
            itemClickListener.onItemClickListener((Integer) view.getTag());
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MainViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.main_rv_item_txt);
        }
    }
}
