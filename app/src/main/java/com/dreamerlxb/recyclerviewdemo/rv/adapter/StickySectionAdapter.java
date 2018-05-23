package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.entity.ISectionEntity;
import com.dreamerlxb.recyclerviewdemo.entity.StickySectionEntityImpl;

import java.util.List;

/**
 * Created by sxb on 2017/3/17.
 * 粘性布局分组
 */

public class StickySectionAdapter<T extends ISectionEntity> extends RecyclerView.Adapter{
    private static final int ITEM_TYPE_SECTION = 2222;
    private static final int ITEM_TYPE_NORMAL = 1111;
    private LayoutInflater inflater;

    public StickySectionAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    private List<T> dataList;

    @Override
    public int getItemViewType(int position) {
        T se = dataList.get(position);
        return se.isSection() ? ITEM_TYPE_SECTION : ITEM_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SECTION) {
            return new SectionViewHolder(inflater.inflate(R.layout.sticky_section_item, parent, false));
        }
        return new ItemViewHolder(inflater.inflate(R.layout.sticky_normal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StickySectionEntityImpl se = (StickySectionEntityImpl) dataList.get(position);
        if (se.isSection()) {
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
       return dataList.get(pos).isSection();
    }

    public T getItemObject(int pos) {
        if (pos >= dataList.size()) {
            return  null;
        }
        return dataList.get(pos);
    }

    public int getSectionForPosition(int position) {
        T obj = dataList.get(position);
        return obj.getSectionId();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sticky_normal_item_txt);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        SectionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sticky_section_item_txt);
        }
    }
}
