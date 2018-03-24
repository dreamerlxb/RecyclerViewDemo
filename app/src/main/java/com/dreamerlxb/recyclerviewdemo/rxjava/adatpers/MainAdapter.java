package com.dreamerlxb.recyclerviewdemo.rxjava.adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by lion on 2018/3/24.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainVH> {
    private List<String> items;

    public MainAdapter(List<String> items) {
        this.items = items;
    }

    public String getItem(int position) {
        return items.get(position);
    }

    public void addItems(List<String> items) {
        this.items.addAll(items);
        notifyItemRangeInserted(items.size() - 1, items.size());
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.main_item, parent, false);
        return new MainVH(v);
    }

    @Override
    public void onBindViewHolder(MainVH holder, int position) {
        String txt = getItem(position);
        holder.titleTv.setText(txt);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MainVH extends RecyclerView.ViewHolder {
        TextView titleTv;

        MainVH(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.main_rv_item_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
