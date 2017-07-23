package com.dreamerlxb.base;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/7.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int ITEM_TYPE_HEADER_BASE = 8888;
    public static final int ITEM_TYPE_NORMAL = 7777;
    public static final int ITEM_TYPE_FOOTER_BASE = 6666;
    public static final int ITEM_TYPE_LOAD_MORE = 5555;

    private Context mContext;
    private SparseArrayCompat<View> mHeaderViews;
    private SparseArrayCompat<View> mFooterViews;
    private BaseLoadMore loadMoreView = new DefaultLoadMore();

    private boolean mEnableLoadMore = false;

    // 是否还有更多数据
    private boolean mHasMore = true;
    // 加载失败
    private boolean mLoadMoreFailed = false;
    // 正在加载更多
    private boolean mLoadingMore = false;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    //加载更多监听
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            // 如果没有启用加载更多，那么不做处理
            if (!mEnableLoadMore) {
                return;
            }
            if(newState == RecyclerView.SCROLL_STATE_IDLE) { // 当recycleView滚动停止时，判断是否到最底部，若到了最底部直接加载更多
                if (!recyclerView.canScrollVertically(1) ) { //判断垂直方向上可否向上移动
                    // 如果是正在加载，那么再次滑动到底部时，就不作处理
                    Log.i("=====", "load more");
                    if (mLoadingMore) {
                        return;
                    }
                    if (mHasMore) {
                        mLoadingMore = true;
                        if (null != mOnLoadMoreListener) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                    } else {
                        mLoadingMore = false;
                        recyclerView.removeOnScrollListener(this);
                    }
                    notifyItemChanged(getItemCount() - 1);
                }
            }
        }
    };

    public BaseAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
        mHeaderViews = new SparseArrayCompat<>();
        mFooterViews = new SparseArrayCompat<>();
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 添加Header key : viewType   value: headerView
     * @param headerView
     */
    public void addHeader(View headerView) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER_BASE, headerView);
    }

    /**
     * 添加footer key : viewType   value: headerView
     * @param footerView
     */
    public void addFooter(View footerView) {
        mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER_BASE, footerView);
    }

    /**
     * 设置加载更多View
     * @param loadMoreView
     */
    public void setLoadMoreView(BaseLoadMore loadMoreView) {
        this.mEnableLoadMore = true;
        this.loadMoreView = loadMoreView;
    }

    /**
     * 设置是否使用加载更多
     * @param mEnableLoadMore
     */
    public void setEnableLoadMore(boolean mEnableLoadMore) {
        this.mEnableLoadMore = mEnableLoadMore;
    }

    public void loadMoreFailed() {
        this.mLoadingMore = false;
        this.mLoadMoreFailed = true;
    }

    // "加载更多"完成时
    public void loadMoreComplete(boolean hasMore) {
        this.mLoadingMore = false;
        this.mHasMore = hasMore;
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
                    if (isHeader(position) || isFooter(position) || isLoadMore( position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    // 添加支持StaggeredGridLayoutManager
    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (null != layoutParams &&
                layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            int pos = holder.getLayoutPosition();
            if (isFooter(pos) || isHeader(pos) || isLoadMore(pos) ) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType, null) != null) { // 头部
            return new HeaderAndFooterViewHolder(mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType, null) != null) {
            return new HeaderAndFooterViewHolder(mFooterViews.get(viewType));
        } else if (viewType == ITEM_TYPE_LOAD_MORE) {
            View lmView = LayoutInflater.from(mContext).inflate(loadMoreView.getLayoutId(), parent, false);
            return new LoadMoreViewHolder(lmView);
        } else {
            return this.onCreateDefViewHolder(parent, viewType);
        }
    }

    public abstract BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(isHeader(position) || isFooter(position)) {
            return;
        }
        if (isLoadMore(position)) {
            if (mLoadMoreFailed) {
                loadMoreView.convert(holder, BaseLoadMore.STATUS_FAIL);
                return;
            }
            if (!mHasMore) {
                loadMoreView.convert(holder, BaseLoadMore.STATUS_NO_MORE);
                return;
            }
            if (mLoadingMore) { // 是否是正在加载状态
                loadMoreView.convert(holder, BaseLoadMore.STATUS_LOADING);
                return;
            }
            if (!mLoadingMore && mHasMore) {
                loadMoreView.convert(holder, BaseLoadMore.STATUS_END);
                return;
            }
            loadMoreView.convert(holder, BaseLoadMore.STATUS_DEFAULT);
            return;
        }
        this.onDefBindViewHolder(holder, position - getHeaderCount());
    }

    public abstract void onDefBindViewHolder(BaseViewHolder holder, int position);

    public boolean isHeader(int position) {
        return position < getHeaderCount();
    }

    public boolean isFooter(int position) {
        return position >= getHeaderCount() + getDefItemCount()
                && position < getItemCount()
                && !isLoadMore(position);
    }

    public boolean isLoadMore(int position) {
        return mEnableLoadMore && position == getItemCount() -1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) { // header 位置
            return mHeaderViews.keyAt(position); // key 即为 该view对应的 itemType
        }

        if (isFooter(position)) { // footer 位置
            return mFooterViews.keyAt(position - getHeaderCount() - getDefItemCount());
        }

        // 使用加载更多
        if (isLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }

        // 正常 item position 从0开始
        return getDefItemViewType(position - getHeaderCount()); // 正常 item 位置
    }

    public int getDefItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {
        return getHeaderCount() + getDefItemCount()  + getFooterCount() + getLoadMoreCount();
    }

    public abstract int getDefItemCount();

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    public int getLoadMoreCount() {
        if (!mEnableLoadMore || null == loadMoreView) {
            return 0;
        }
        return 1;
    }

    public class HeaderAndFooterViewHolder extends BaseViewHolder {
        public HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class LoadMoreViewHolder extends BaseViewHolder {
        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
