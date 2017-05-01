package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.entity.SectionDecorEntity;
import com.dreamerlxb.recyclerviewdemo.entity.SectionEntity;
import com.dreamerlxb.recyclerviewdemo.entity.StickySectionEntityImpl;

import java.util.List;

/**
 * Created by sxb on 2017/3/17.
 * 分组 （装饰布局）
 */

public class SectionDecorAdapter<T extends SectionEntity> extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_SECTION = 2222;
    public static final int ITEM_TYPE_NORMAL = 1111;
    LayoutInflater inflater;

    public SectionDecorAdapter(Context context, List<T> dataList) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    private List<T> dataList;

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.sticky_normal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T se = dataList.get(position);
        SectionDecorEntity ssei = (SectionDecorEntity) se;
        ItemViewHolder svh = (ItemViewHolder) holder;
        svh.textView.setText(ssei.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
        public TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sticky_normal_item_txt);
        }
    }
}
