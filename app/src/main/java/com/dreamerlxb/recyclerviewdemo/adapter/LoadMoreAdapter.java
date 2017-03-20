package com.dreamerlxb.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sxb on 2017/3/15.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_LOAD_MORE = 7777;
    private Context context;
    private NormalAdapter adapter;
    private View loadMoreView;

    public interface LoadMoreListener {
        void onLoadMore();
    }

    public LoadMoreAdapter(Context context) {
        this.context = context;
    }

    public LoadMoreAdapter(Context context, NormalAdapter adapter) {
        this(context);
        this.adapter = adapter;
    }

    public void setLoadMoreView(View view) {
        this.loadMoreView = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) { // load more
            return new LoadMoreViewHolder(loadMoreView);
        }
        return adapter.createViewHolder(parent, viewType);
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
                    if (position + 1 == getItemCount()) { // 说明是load more
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
            if (holder.getLayoutPosition() + 1 == getItemCount()) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position + 1 == getItemCount()) {
            return;
        }
        adapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getRealItemCount() + 1;
    }

    public int getRealItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) { // load more view
            return ITEM_TYPE_LOAD_MORE; // key 即为 该view对应的 itemType
        }
        // 正常 item position 从0开始
        return adapter.getItemViewType(position); // 正常 item 位置
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
