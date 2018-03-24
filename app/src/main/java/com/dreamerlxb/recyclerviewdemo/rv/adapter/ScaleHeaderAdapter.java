package com.dreamerlxb.recyclerviewdemo.rv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dreamerlxb.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/5/1.
 */

public class ScaleHeaderAdapter extends RecyclerView.Adapter {
    private static final int IS_HEADER = 1000;
    private static final int IS_NORMAL = 1001;
    private LayoutInflater inflater;
    private Context mContext;

    private OnTouchClick mOnTouchClick;

    public void setOnTouchClick(OnTouchClick onTouchClick) {
        mOnTouchClick = onTouchClick;
    }

    public interface OnTouchClick {
        void onTouch(ImageView view);
    }


    public ScaleHeaderAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case IS_HEADER:
                View view = inflater.inflate(R.layout.scale_header, parent, false);
                viewHolder = new ScaleHeaderViewHolder(view);
                break;
            case IS_NORMAL:
                View view1 = inflater.inflate(R.layout.normal_item, parent, false);
                viewHolder = new ItemViewHolder(view1);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            ScaleHeaderViewHolder scaleHeaderViewHolder = (ScaleHeaderViewHolder) holder;
            mOnTouchClick.onTouch(scaleHeaderViewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        return  position == 0 ? IS_HEADER : IS_NORMAL;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ScaleHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ScaleHeaderViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_header);
        }
    }
}
