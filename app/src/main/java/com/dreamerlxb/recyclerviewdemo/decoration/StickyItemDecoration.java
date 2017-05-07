package com.dreamerlxb.recyclerviewdemo.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.entity.MarkType;
import com.dreamerlxb.recyclerviewdemo.util.Utities;

/**
 * Created by Administrator on 2017/3/19.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    public interface StickyItemDecorationCb {
        /**
         *  获取position对应位置的sectionId
         * @param position
         * @return
         */
        int getSectionId(int position);

        /**
         * 获取position对应的Section Item
         * @param position
         * @return
         */
        Object getSectionItem(int position);
    }
    private StickyItemDecorationCb stickyDecorationCb;
    private Paint sectionPaint;
    private Paint textPaint;
    private int topGap;
    private Context context;

    public StickyItemDecoration(int topGap, StickyItemDecorationCb sectionDecorationCb) {
        this.stickyDecorationCb = sectionDecorationCb;
        this.topGap = topGap;
        this.stickyDecorationCb = sectionDecorationCb;
        sectionPaint = new Paint();
        sectionPaint.setColor(Color.argb(255, 240, 240, 240));

        textPaint = new Paint();
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(60);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    public StickyItemDecoration(Context context, StickyItemDecorationCb stickyDecorationCb) {
        this(context.getResources().getDimensionPixelSize(R.dimen.sectioned_top), stickyDecorationCb);
        this.context = context;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
//        Log.i("==childCount==", childCount + "");
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int preSectionId, sectionId = -1;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            /*
            * getChildAdapterPosition: 获取VIew在adapter中的位置
            * getChildLayoutPosition: 获取View在layout中的位置
            * */
            int position = parent.getChildAdapterPosition(view);
            preSectionId = sectionId; // 记录上一个section的id
            sectionId = stickyDecorationCb.getSectionId(position);

            if (sectionId < 0 || sectionId == preSectionId) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { // 如果position是最后一个，就不需要判断了
                // 下一个View和当前View不在同一组
                int nextSectionId = stickyDecorationCb.getSectionId(position + 1);
                if (nextSectionId != sectionId && viewBottom < textY ) { // 组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
//            Log.i("==here==", "======");

            MarkType mt = (MarkType)stickyDecorationCb.getSectionItem(position);

            int resId = context.getResources().getIdentifier(mt.getTypeName().toLowerCase(),"mipmap",context.getPackageName());
            Drawable leftDrawable = ContextCompat.getDrawable(context, resId);

            Bitmap bitmap = Utities.drawableToBitmap(leftDrawable);
            c.drawRect(left, textY - topGap, right, textY, sectionPaint);
            // 计算 text的 baseline
            float baselineY = textY - topGap + (topGap - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;

            c.drawText(mt.getTypeDesc(), left + bitmap.getWidth() + 20, baselineY, textPaint);
            // bitmap 的Y坐标
            float bitmapTop = textY -topGap + topGap/2 - bitmap.getHeight()/2;
            c.drawBitmap(bitmap, left, bitmapTop, textPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        int sectionId = stickyDecorationCb.getSectionId(pos);
        if (sectionId < 0) {
            return;
        }
        // 只有每一个分组的第一个才绘制
        if (isFirstInSection(pos)) {
            outRect.top = topGap; // 目前先固定
        } else {
            outRect.top = 0;
        }
    }

    /***
     * 判断该位置是否为它所在分区的第一个
     * @param pos
     * @return
     */
    private boolean isFirstInSection(int pos) {
        if (pos == 0) {
            return true;
        } else {
            // 获取上一个位置的 sectionid
            int prevSectionId = stickyDecorationCb.getSectionId(pos-1);
            int sectionId = stickyDecorationCb.getSectionId(pos);
            return prevSectionId != sectionId;
        }
    }
}
