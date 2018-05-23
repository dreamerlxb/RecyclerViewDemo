package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sxb on 2017/3/15.
 */

public class HeaderAndFooterAdapter extends RecyclerView.Adapter {
    private static final int ITEM_TYPE_HEADER_BASE = 8888;

    private static final int ITEM_TYPE_FOOTER_BASE = 6666;
    private Context context;
    private NormalAdapter adapter;
    private SparseArrayCompat<View> headerViews;
    private SparseArrayCompat<View> footerViews;

    public HeaderAndFooterAdapter(Context context) {
        this.context = context;
        headerViews = new SparseArrayCompat<>();
        footerViews = new SparseArrayCompat<>();
    }

    public HeaderAndFooterAdapter(Context context, NormalAdapter adapter) {
        this(context);
        this.adapter = adapter;
    }

    public void addHeader(View headerView) {
        headerViews.put(headerViews.size() + ITEM_TYPE_HEADER_BASE, headerView);
    }

    public void addFooter(View footerView) {
        footerViews.put(footerViews.size() + ITEM_TYPE_FOOTER_BASE, footerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (headerViews.get(viewType, null) != null) { // 头部
            return new HeaderAndFooterViewHolder(headerViews.get(viewType));
        } else if (footerViews.get(viewType, null) != null) {
            return new HeaderAndFooterViewHolder(footerViews.get(viewType));
        }
        return adapter.createViewHolder(parent, viewType);
    }
    // Adapter 吸附到 RecyclerView 时
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.DefaultSpanSizeLookup());
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeader(position) || isFooter(position)) {
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
        if (null != layoutParams && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isFooter(holder.getLayoutPosition()) || isHeader(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    public boolean isHeader(int position) {
        return position < getHeaderCount();
    }

    public boolean isFooter(int position) {
        return position >= getHeaderCount() + getRealItemCount();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isHeader(position) || isFooter(position)) {
            return;
        }
        adapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getRealItemCount() + getFooterCount();
    }

    public int getRealItemCount() {
        return adapter.getItemCount();
    }

    public int getHeaderCount() {
        return headerViews.size();
    }

    public int getFooterCount() {
        return footerViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) { // header 位置
            return headerViews.keyAt(position); // key 即为 该view对应的 itemType
        } else if (isFooter(position)) { // footer 位置
            return footerViews.keyAt(position - getHeaderCount() - getRealItemCount());
        }
        // 正常 item position 从0开始
        return adapter.getItemViewType(position - getHeaderCount()); // 正常 item 位置
    }

    public class HeaderAndFooterViewHolder extends RecyclerView.ViewHolder {
        public HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
