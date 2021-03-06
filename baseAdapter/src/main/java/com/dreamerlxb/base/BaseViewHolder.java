package com.dreamerlxb.base;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * @author lxb
 * Created by Administrator on 2017/5/7.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void setVisible(@IdRes int viewId, boolean visible) {
        View v = getView(viewId);
        if (visible) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.INVISIBLE);
        }
    }

    public void setGone(@IdRes int viewId, boolean gone) {
        View v = getView(viewId);
        if (gone) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
    }

    public void setText(@IdRes int viewId, CharSequence text) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            ((TextView)v).setText(text);
        }
    }

    public void setText(@IdRes int viewId, @StringRes int stringRes) {
        View v = getView(viewId);
        if (v instanceof TextView) {
            ((TextView)v).setText(stringRes);
        }
    }
}
