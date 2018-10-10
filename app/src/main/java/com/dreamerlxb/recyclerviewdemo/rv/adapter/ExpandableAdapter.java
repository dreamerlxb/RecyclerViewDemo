package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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


public class ExpandableAdapter extends RecyclerView.Adapter {

    private static final int ITEM_TYPE_SECTION = 2222;
    private static final int ITEM_TYPE_NORMAL = 1111;

    private LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<SectionEntityImpl> dataList) {
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    private List<SectionEntityImpl> dataList;

    @Override
    public int getItemViewType(int position) {
        SectionEntityImpl se = dataList.get(position);
        return se.isSection() ? ITEM_TYPE_SECTION : ITEM_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SECTION) {
            return new SectionViewHolder(inflater.inflate(R.layout.section_item, parent, false));
        }
        return new ItemViewHolder(inflater.inflate(R.layout.normal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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

    public void open(int position) {
        SectionEntityImpl s = dataList.get(position);
        List<SectionEntityImpl> items = s.getSubItems();

        if (s.isExpanded()) {
            if(items != null) {
                dataList.subList(position + 1, position + 1 + items.size()).clear();
                s.setExpanded(false);
                notifyItemRangeRemoved(position + 1, items.size());
            }
        } else {
            if (items != null) {
                dataList.addAll(position + 1, items);
                s.setExpanded(true);
                notifyItemRangeInserted(position + 1, items.size());
            }
        }
    }

    // Adapter 吸附到 RecyclerView 时
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
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
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
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
            textView = itemView.findViewById(R.id.rv_item_txt);
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.section_txt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    open(p);
                }
            });
        }
    }
}

