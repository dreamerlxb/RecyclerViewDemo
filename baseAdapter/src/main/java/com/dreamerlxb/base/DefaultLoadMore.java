package com.dreamerlxb.base;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/5/7.
 */

public class DefaultLoadMore extends BaseLoadMore {

    @Override
    public int getLayoutId() {
        return R.layout.default_base_adapter_load_more;
    }

    @Override
    public void convert(BaseViewHolder holder, int loadMoreStatus) {
        this.setLoadMoreStatus(loadMoreStatus);
        switch (loadMoreStatus) {
            case STATUS_LOADING:
                holder.getView(R.id.default_pb).setVisibility(View.VISIBLE);
                TextView tv = holder.getView(R.id.default_tv);
                tv.setText("加载中...");
                break;
            case STATUS_FAIL:
                holder.getView(R.id.default_pb).setVisibility(View.GONE);
                TextView tv2 = holder.getView(R.id.default_tv);
                tv2.setText("加载失败，点击重试");
                break;
            case STATUS_END:
                holder.getView(R.id.default_pb).setVisibility(View.GONE);
                TextView tv3 = holder.getView(R.id.default_tv);
                tv3.setText("加载完成");
                break;
            case STATUS_NO_MORE:
                holder.getView(R.id.default_pb).setVisibility(View.GONE);
                TextView tv5 = holder.getView(R.id.default_tv);
                tv5.setText("数据加载完成，没有更多了");
                break;
            case STATUS_DEFAULT:
                holder.getView(R.id.default_pb).setVisibility(View.GONE);
                TextView tv4 = holder.getView(R.id.default_tv);
                tv4.setText("加载更多");
                break;
        }
    }
}
