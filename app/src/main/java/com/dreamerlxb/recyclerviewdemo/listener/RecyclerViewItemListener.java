package com.dreamerlxb.recyclerviewdemo.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RecyclerViewItemListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetector gestureDetector;

    public void onClick(View view, int position) {

    }

    public RecyclerViewItemListener(final RecyclerView recyclerView) {
        GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                int pos = recyclerView.getChildAdapterPosition(view);
                RecyclerViewItemListener.this.onClick(view, pos);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        };
        gestureDetector = new GestureDetector(recyclerView.getContext(), onGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

//    @Override
//    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//    }
//
//    @Override
//    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//    }
}
