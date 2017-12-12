package com.dreamerlxb.base;

import android.support.annotation.LayoutRes;

/**
 * Created by Administrator on 2017/5/7.
 */

public abstract class BaseLoadMore {
    //  默认
    public static final int STATUS_DEFAULT = 1;
    // 加载状态
    public static final int STATUS_LOADING = 2;
    // 加载失败状态
    public static final int STATUS_FAIL = 3;
    // 加载结束状态(加载成功并且还有数据)
    public static final int STATUS_END = 4;
    // 没有数据状态（加载成功并且没有数据）
    public static final int STATUS_NO_MORE = 5;
    // 还有数据状态
    public static final int STATUS_HAS_MORE = 1;

    private int mLoadMoreStatus = STATUS_DEFAULT;

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    /**
     * load more layout
     *
     * @return
     */
    public abstract
    @LayoutRes
    int getLayoutId();
    public abstract void convert(BaseViewHolder holder, int loadMoreStatus);
}
