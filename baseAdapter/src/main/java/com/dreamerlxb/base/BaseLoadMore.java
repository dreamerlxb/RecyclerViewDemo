package com.dreamerlxb.base;

import android.support.annotation.LayoutRes;

/**
 * Created by Administrator on 2017/5/7.
 */

public abstract class BaseLoadMore {
    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;
    public static final int STATUS_NO_MORE = 5;

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
