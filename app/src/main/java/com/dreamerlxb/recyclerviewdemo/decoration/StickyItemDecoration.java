package com.dreamerlxb.recyclerviewdemo.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/3/19.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    public interface StickyItemDecorationCb {
        int getSectionId(int position);
    }
    private StickyItemDecorationCb stickyDecorationCb;
    private Paint sectionPaint;
    private Paint textPaint;
    private int topGap;

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

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        long preSectionId, sectionId = -1;
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
                // 下一个和当前不一样移动当前
                int nextSectionId = stickyDecorationCb.getSectionId(position + 1);
                if (nextSectionId != sectionId && viewBottom < textY ) { // 组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, sectionPaint);
            // 计算 text的 baseline
            float baselineY = textY - topGap + (topGap - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            c.drawText("Section" + sectionId, left, baselineY, textPaint);
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
