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
import com.dreamerlxb.recyclerviewdemo.entity.SectionEntityImpl;

import java.util.List;
import java.util.Map;


public class ExpandableAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_SECTION = 2222;
    public static final int ITEM_TYPE_NORMAL = 1111;

    LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<SectionEntityImpl> dataList,
                             Map<Integer, List<SectionEntityImpl>> items) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);

        subItems = items;
    }

    private List<SectionEntityImpl> dataList;

    private Map<Integer, List<SectionEntityImpl>> subItems;

    @Override
    public int getItemViewType(int position) {
        SectionEntityImpl se = dataList.get(position);
        return se.isSection() ? ITEM_TYPE_SECTION : ITEM_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NORMAL) {
            return new ItemViewHolder(inflater.inflate(R.layout.normal_item, parent, false));
        } else if (viewType == ITEM_TYPE_SECTION) {
            return new SectionViewHolder(inflater.inflate(R.layout.section_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionEntityImpl se = dataList.get(position);
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

    // Adapter 吸附到 RecyclerView 时
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isSection(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * 添加支持StaggeredGridLayoutManager
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (null != layoutParams && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            int layoutPos = holder.getLayoutPosition();
            if (isSection(layoutPos)) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    SectionEntityImpl s = dataList.get(p);
                    List<SectionEntityImpl> items = subItems.get(p);
                    if (s.isExpanded()) {
                        if(items != null) {
                            dataList.subList(p + 1, p + 1 + items.size()).clear();
                            s.setExpanded(false);
                            notifyItemRangeRemoved(p+1, items.size());
                        }
                    } else {
                        if (items != null) {
                            dataList.addAll(p + 1, items);
                            s.setExpanded(true);
                            notifyItemRangeChanged(p+1, items.size());
                        }
                    }
                }
            });
        }
    }
}

