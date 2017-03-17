package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxb on 2017/3/15.
 */

public class HeaderAndFooterAdapter extends RecyclerView.Adapter {
    public static final int ITEM_HEADER = 0;
//    public static final int ITEM_FOOTER = 2;
    private Context context;
    private NormalAdapter adapter;
    private SparseArrayCompat<View> headerViews;
//    private SparseArray<View> footerViews;

    public HeaderAndFooterAdapter(Context context) {
        this.context = context;
        headerViews = new SparseArrayCompat<>();
//        footerViews = new SparseArray<>();
    }

    public HeaderAndFooterAdapter(Context context, NormalAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public void addHeader(View headerView) {
        headerViews.put(headerViews.size(), headerView);
//        notifyItemInserted(headerViews.size() - 1);
    }

//    public void addFooter(View footerView) {
//        footerViews.put(footerViews.size(), footerView);
////        notifyItemInserted(footerViews.size() - 1);
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_HEADER) { // 头部
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_header_item, null);
            return new RecyclerViewHeaderHolder(view);
        }
        return adapter.createViewHolder(parent, viewType);
    }
    // Adapter 吸附到 RecyclerView 时
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager) {
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.DefaultSpanSizeLookup());
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (getItemViewType(position) == ITEM_HEADER) {
//                        return gridLayoutManager.getSpanCount();
//                    }
//                    return 1;
//                }
//            });
//        }
//    }


//    /**
//     * 添加支持StaggeredGridLayoutManager
//     * @param holder
//     */
//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//        if (null != layoutParams && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
//            if (isFooterView(holder.getLayoutPosition()) || isHeaderView(holder.getLayoutPosition())) {
//                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
//                params.setFullSpan(true);
//            }
//        }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            return;
        }
        adapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return headerViews.size() + adapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headerViews.size()) {
            return ITEM_HEADER;
        }
        return adapter.getItemViewType(position);
    }

    public class RecyclerViewHeaderHolder extends RecyclerView.ViewHolder {

        public RecyclerViewHeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
