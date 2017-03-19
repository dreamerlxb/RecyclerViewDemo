package com.dreamerlxb.recyclerviewdemo.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    public SectionItemDecoration(SectionItemDecorationCb sectionDecorationCb) {
        this.sectionDecorationCb = sectionDecorationCb;
        sectionPaint = new Paint();
        sectionPaint.setColor(Color.RED);
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
//            int sectionId = sectionDecorationCb.getSectionId(pos);
//            if (sectionId < 0) return;
            if (isFirstInSection(pos)) {
                float top = view.getTop() - 32;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, sectionPaint);
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
            outRect.top = 32; // 目前先固定
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
