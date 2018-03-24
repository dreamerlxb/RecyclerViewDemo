package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sxb on 2017/3/15.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_LOAD_MORE = 7777;
    private Context context;

    private NormalAdapter adapter;
    private View loadingMoreView;
    private View loadMoreFailedView;
    private View loadMoreNoDataView;
    private LoadMoreListener loadMoreListener;

    private boolean hasMore; // 是否还有更多数据

    public interface LoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_IDLE) { // 当recycleView滚动停止时，判断是否到最底部，若到了最底部直接加载更多
                if (!recyclerView.canScrollVertically(1) ) { //判断垂直方向上可否向上移动
                    // 在这加载更多数据 (数据已经加载完成)
                    if (hasMore) {
                        loadMoreListener.onLoadMore();
                    } else {
                        notifyItemChanged(getItemCount() - 1);
                        recyclerView.removeOnScrollListener(this);
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    public LoadMoreAdapter(RecyclerView recyclerView, NormalAdapter adapter) {
        this.context = recyclerView.getContext();
        this.adapter = adapter;
        this.hasMore = true;
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public void setLoadingMoreView(View view) {
        this.loadingMoreView = view;
    }

    public void setLoadMoreFailedView(View loadMoreFailedView) {
        this.loadMoreFailedView = loadMoreFailedView;
    }

    public void setLoadMoreNoDataView(View loadMoreNoDataView) {
        this.loadMoreNoDataView = loadMoreNoDataView;
    }

    public void loadMoreFailed() {

    }

    public void loadMoreFinish(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) { // load more
            if (hasMore) {
                Log.i("=======", hasMore+"");
                return new LoadMoreViewHolder(loadingMoreView);
            } else {
                Log.i("=======", hasMore+"");
                return new LoadMoreViewHolder(loadMoreNoDataView);
            }
        }
        return adapter.createViewHolder(parent, viewType);
    }
    // 判断布局类型
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
     * 当View吸附到window时调用
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
        LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
