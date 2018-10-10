package com.dreamerlxb.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamerlxb.base.BaseLoadMore;
import com.dreamerlxb.base.BaseViewHolder;
import com.dreamerlxb.base.DefaultLoadMore;

/**
 * @author lxbp
 * Created by Administrator on 2017/5/7.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int ITEM_TYPE_HEADER_BASE = 8888;
    public static final int ITEM_TYPE_NORMAL = 7777;
    public static final int ITEM_TYPE_EMPTY = 5550;
    public static final int ITEM_TYPE_FOOTER_BASE = 6666;
    public static final int ITEM_TYPE_LOAD_MORE = 5555;

    private Context mContext;
    private SparseArrayCompat<View> mHeaderViews;
    //    private View mEmptyView;
//    private @IdRes
//    int mEmptyLayoutId;
    private SparseArrayCompat<View> mFooterViews;
    private BaseLoadMore loadMoreView = new DefaultLoadMore();

    private boolean mEnableLoadMore = false;

    // item 开始位置（如果没有header,那么就是0，否则就是header的个数）
    // (header的个数)
    private int itemStartCursor = 0;

    // load more 状态
    private int mLoadMoreStatus = BaseLoadMore.STATUS_DEFAULT;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    //加载更多监听
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    /**
     * 根据 滚动状态判断加载
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            // 如果没有启用加载更多，那么不做处理
            if (!mEnableLoadMore) {
                return;
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 当recycleView滚动停止时，判断是否到最底部，若到了最底部直接加载更多
                if (!recyclerView.canScrollVertically(1)) { // 判断垂直方向上可否向上移动
                    // 如果是正在加载，那么再次滑动到底部时，就不作处理
                    Log.i("=====", "load more");
                    if (mLoadMoreStatus == BaseLoadMore.STATUS_LOADING) {
                        return;
                    }
                    // 如果是加载失败状态，那么重新加载
                    if (mLoadMoreStatus == BaseLoadMore.STATUS_FAIL) {
                        mLoadMoreStatus = BaseLoadMore.STATUS_HAS_MORE;
                    }
                    // mHasMore
                    if (mLoadMoreStatus == BaseLoadMore.STATUS_HAS_MORE
                            || mLoadMoreStatus == BaseLoadMore.STATUS_END) {
                        mLoadMoreStatus = BaseLoadMore.STATUS_LOADING;
                        if (null != mOnLoadMoreListener) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                    } else { // 没有数据了，移除监听
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
     *
     * @param headerView
     */
    public void addHeader(View headerView) {
        mHeaderViews.put(itemStartCursor + ITEM_TYPE_HEADER_BASE, headerView);
        itemStartCursor += 1;
    }

    /**
     * 添加footer key : viewType   value: headerView
     *
     * @param footerView
     */
    public void addFooter(View footerView) {
        mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER_BASE, footerView);
    }

    /**
     * 设置加载更多View
     *
     * @param loadMoreView
     */
    public void setLoadMoreView(BaseLoadMore loadMoreView) {
        this.mEnableLoadMore = true;
        this.loadMoreView = loadMoreView;
    }

    /**
     * 设置是否使用加载更多
     *
     * @param mEnableLoadMore
     */
    public void setEnableLoadMore(boolean mEnableLoadMore) {
        this.mEnableLoadMore = mEnableLoadMore;
    }

    public void loadMoreFailed() {
        mLoadMoreStatus = BaseLoadMore.STATUS_FAIL;
    }

    // "加载更多"完成时
    public void loadMoreComplete(boolean hasMore) {
        if (hasMore) {
            this.mLoadMoreStatus = BaseLoadMore.STATUS_END;
        } else {
            this.mLoadMoreStatus = BaseLoadMore.STATUS_NO_MORE;
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
                    if (isHeader(position) || isEmpty(position) || isFooter(position) || isLoadMore(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    // 添加支持StaggeredGridLayoutManager
    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (null != layoutParams &&
                layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            int pos = holder.getLayoutPosition();
            if (isFooter(pos) || isEmpty(pos) || isHeader(pos) || isLoadMore(pos)) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    @NonNull
    @SuppressLint("ResourceType")
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType, null) != null) { // 头部
            return new HeaderAndFooterViewHolder(mHeaderViews.get(viewType));
        } else if (viewType == ITEM_TYPE_EMPTY) {
            View mEmptyView = LayoutInflater.from(parent.getContext()).inflate(getEmptyLayoutId(), parent, false);
            return new EmptyViewHolder(mEmptyView);
        } else if (mFooterViews.get(viewType, null) != null) {
            return new HeaderAndFooterViewHolder(mFooterViews.get(viewType));
        } else if (viewType == ITEM_TYPE_LOAD_MORE) {
            View lmView = LayoutInflater.from(mContext).inflate(loadMoreView.getLayoutId(), parent, false);
            return new LoadMoreViewHolder(lmView);
        } else {
            return this.onCreateDefViewHolder(parent, viewType);
        }
    }

    /**
     * 创建普通 items
     * @param parent
     * @param viewType
     * @return
     */
    public abstract BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType);

    /**
     * 返回empty layout id
     *  <p>默认返回0</p>
     * @return
     */
    public @IdRes int getEmptyLayoutId() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (isHeader(position) || isEmpty(position) || isFooter(position)) {
            return;
        }
        if (isLoadMore(position)) {
            loadMoreView.convert(holder, mLoadMoreStatus);
            return;
        }
        this.onDefBindViewHolder(holder, position - getHeaderCount());
    }

    /**
     * 为item绑定数据
     *
     * @param holder   @see BaseViewHolder
     * @param position 真实item的位置
     */
    public abstract void onDefBindViewHolder(BaseViewHolder holder, int position);

    /**
     * 是否为 headerView
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position < getHeaderCount();
    }

    /**
     * 是否为 emptyView
     * @param position
     * @return
     */
    public boolean isEmpty(int position) {
        return  getEmptyLayoutId() != 0 &&
                getDefItemCount() > 0 &&
                position == getHeaderCount();
    }

    /**
     * 是否为footer
     * <p></p>
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        return position >= getHeaderCount() + getEmptyCount() + getDefItemCount()
                && position < getItemCount()
                && !isLoadMore(position);
    }

    /**
     * 最后一个为loadMore
     * @param position
     * @return
     */
    public boolean isLoadMore(int position) {
        return mEnableLoadMore && position == getItemCount() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) { // header 位置
            return mHeaderViews.keyAt(position); // key 即为 该view对应的 itemType
        }

        if (isEmpty(position)) {
            return ITEM_TYPE_EMPTY;
        }

        if (isFooter(position)) { // footer 位置
            return mFooterViews.keyAt(position - getHeaderCount() - getEmptyCount() - getDefItemCount());
        }

        // 使用加载更多
        if (isLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }

        // 正常 item position 从0开始
        return getDefItemViewType(position - getHeaderCount()); // 正常 item 位置
    }

    /**
     * 除去header、empty、footer、loadMore之后的其他Items
     * @param position
     * @return
     */
    public int getDefItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }

    /**
     * item个数
     * <p>包括header, empty、footer、loadMore</p>
     *
     * @return item总个数
     */
    @Override
    public int getItemCount() {
        return getHeaderCount() + getEmptyCount() + getDefItemCount() + getFooterCount() + getLoadMoreCount();
    }

    /**
     * 真实的item个数
     * <p>不包括header、empty和footer个数</p>
     *
     * @return item个数
     */
    public abstract int getDefItemCount();

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    /**
     * 返回emptyView的个数
     * <p>如果存在emptyView并且存在items，那么就返回1，否则返回0</p>
     * @return
     */
    public int getEmptyCount() {
        // 如果没有数据，那么就存在emptyView
        if (getEmptyLayoutId() != 0 && getDefItemCount() == 0) {
            return 1;
        }
        return 0;
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

    public class EmptyViewHolder extends BaseViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
