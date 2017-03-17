package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.entity.SectionEntity;

import java.util.List;

/**
 * Created by sxb on 2017/3/17.
 * 分组
 */

public class StickyAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_SECTION = 2222;
    public static final int ITEM_TYPE_NORMAL = 1111;
    private LayoutInflater inflater;

    public StickyAdapter(Context context, List<SectionEntity> dataList) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    private List<SectionEntity> dataList;

    @Override
    public int getItemViewType(int position) {
        SectionEntity se = dataList.get(position);
        return se.isHeader() ? ITEM_TYPE_SECTION : ITEM_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return new ItemViewHolder(inflater.inflate(R.layout.normal_item, null));
        } else if (viewType == ITEM_TYPE_SECTION) {
            return new SectionViewHolder(inflater.inflate(R.layout.section_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionEntity se = dataList.get(position);
        if (se.isHeader()) {
            SectionViewHolder svh = (SectionViewHolder) holder;
            svh.textView.setText(se.getTitle());
        } else {
            ItemViewHolder svh = (ItemViewHolder) holder;
            svh.textView.setText(se.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 判断是否为section
     * @param pos
     * @return
     */
    public boolean isSection(int pos) {
        if (pos >= dataList.size()) {
            return false;
        }
       return dataList.get(pos).isHeader();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rv_item_txt);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.section_txt);
        }
    }
}
