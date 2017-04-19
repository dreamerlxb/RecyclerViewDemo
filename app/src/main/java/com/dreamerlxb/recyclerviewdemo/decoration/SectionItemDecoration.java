package com.dreamerlxb.recyclerviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dreamerlxb.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/3/19.
 */

public class SectionItemDecoration extends RecyclerView.ItemDecoration {
    public interface SectionItemDecorationCb {
        int getSectionId(int position);
    }

    public void setSectionDecorationCb(SectionItemDecorationCb sectionDecorationCb) {
        this.sectionDecorationCb = sectionDecorationCb;
    }

    private SectionItemDecorationCb sectionDecorationCb;
    private Paint sectionPaint;
    private Paint textPaint;
    private int topGap;


    public SectionItemDecoration(int topGap, SectionItemDecorationCb sectionDecorationCb) {
        this.sectionDecorationCb = sectionDecorationCb;
        sectionPaint = new Paint();
        sectionPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);
        this.topGap = topGap ;//32dp
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);
            int sectionId = sectionDecorationCb.getSectionId(pos);
            if (isFirstInSection(pos)) {
                float top = view.getTop() - topGap;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, sectionPaint);
//                float b = (bottom - top) / 2;
//                textPaint.measureText()
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                // 居中显示
                // topGap: section的高度 （即分区的高度）
                // fontMetrics.bottom - fontMetrics.top : 文字的高度（top为负值，bottom为正值: 以baseline为原点）
                /*
                ---------------------------------------------------------
                * -------------top (-)                                  |
                * -------------                                         |
                * -------------baseline (以基线为坐标原点)             topGap
                * -------------bottom  (+)                              |
                ---------------------------------------------------------
                *
                * drawText中的x 和 y 为baseline的坐标
                * (tg - b + t)/2 + (b-t) / 2 + top
                *
                * (tg- b + t + b -t)/2 + top
                * */
                float baselineY = top + (topGap - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                c.drawText("Section" + sectionId, left, baselineY , textPaint);//绘制文本
            }
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        int sectionId = sectionDecorationCb.getSectionId(pos);
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
            int prevSectionId = sectionDecorationCb.getSectionId(pos-1);
            int sectionId = sectionDecorationCb.getSectionId(pos);
            return prevSectionId != sectionId;
        }

    }
}
