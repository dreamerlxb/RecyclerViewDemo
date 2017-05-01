package com.dreamerlxb.recyclerviewdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.support.v7.widget.AppCompatButton;

/**
 * Created by sxb on 16/9/12.
 */

public class LeftDrawableButton extends AppCompatButton {
    private final static String TAG = "LeftDrawableButton";
    private Drawable[] drawables;
    private float textWidth;
    private float bodyWidth;

    public LeftDrawableButton(Context context) {
        super(context);
        init();
    }

    public LeftDrawableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftDrawableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        drawables = getCompoundDrawables();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        textWidth = getPaint().measureText(getText().toString());

        Drawable drawableLeft = drawables[0]; // 0 top, 1 left, 2 right, 3 bottom
        int totalWidth = getWidth();

        if (drawableLeft != null) {
//            int drawableLeftHeight = drawableLeft.getIntrinsicHeight()/2;
//            int drawableLeftWidth = drawableLeft.getIntrinsicWidth()/2;
//
//            int h = (getHeight()-drawableLeftHeight)/2;
//            drawableLeft.setBounds(0,0,drawableLeftWidth,drawableLeftHeight);
            int drawableWidth = drawableLeft.getIntrinsicWidth();
            int drawablePadding = getCompoundDrawablePadding();
            bodyWidth = textWidth + drawableWidth + drawablePadding;
            setPadding(0,0,(int)(totalWidth - bodyWidth),0);
        }
    }

    public void setText(String text){
        if(text.equals(getText().toString()))
            return;
        super.setText(text);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        canvas.translate((width - bodyWidth) / 2, 0);
        super.onDraw(canvas);
    }
}
