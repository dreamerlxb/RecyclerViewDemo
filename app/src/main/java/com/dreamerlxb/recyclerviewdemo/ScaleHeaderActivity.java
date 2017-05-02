package com.dreamerlxb.recyclerviewdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamerlxb.recyclerviewdemo.adapter.ScaleHeaderAdapter;

public class ScaleHeaderActivity extends AppCompatActivity {

    private RecyclerView mAnim_rv;
    private View scaleView;
//    private DisplayMetrics metric;
//    private boolean mScaling = false;
    private float mFirstPosition = 0;
    private ViewGroup.MarginLayoutParams zoomViewParams;
    private int zoomViewHeight;

    private ScaleHeaderAdapter mTransAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mScaling = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_header);

        mAnim_rv = (RecyclerView) findViewById(R.id.recyclerView);

        if (mLinearLayoutManager == null) {
            mLinearLayoutManager = new LinearLayoutManager(this);
        }
        mAnim_rv.setLayoutManager(mLinearLayoutManager);
        if (mTransAdapter == null) {
            mTransAdapter = new ScaleHeaderAdapter(this);
        }
        mAnim_rv.setAdapter(mTransAdapter);
        mTransAdapter.setOnTouchClick(new ScaleHeaderAdapter.OnTouchClick() {
            @Override
            public void onTouch(ImageView view) {
                scaleView = view;
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        zoomViewParams = (ViewGroup.MarginLayoutParams) scaleView.getLayoutParams();
                        zoomViewHeight = zoomViewParams.height;
                        Log.i("==zoom view==", "width: " + zoomViewParams.width + "    height: " + zoomViewHeight);
                    }
                });
            }
        });

        mAnim_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scaleView.getLayoutParams();
                switch (event.getAction()) {
                    /*
                    Log.i("======", "action down");
                        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                            Log.i("=is top=", "first yes");
                            mFirstPosition = event.getY();
                            isTop = true;
                        } else {
                            Log.i("=is top=", "first no");
                            isTop = false;
                            mFirstPosition = 0;
                        }
                    */
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("======", " action up");
                        if (mScaling) {
                            replyImage();
                            mScaling = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("======", "action move");
                        if (!mScaling) {
                            int p = mLinearLayoutManager.findFirstVisibleItemPosition();
                            if (p == 0 && mLinearLayoutManager.findViewByPosition(p).getTop() == 0) {
                                mFirstPosition = event.getY();
                            } else {
                                break;
                            }
                        }

                        int distance = (int) ((event.getY() - mFirstPosition)* 0.6); // 滚动距离乘以一个系数
                        if (distance < 0) {
                            break;
                        }

//                        int height = (int) (zoomViewHeight + (event.getY() - mFirstPosition) / 1.5 + 0.5); // 滚动距离乘以一个系数
//                        Log.i("====", zoomViewHeight + "   " + height);
//                        if (height <=  zoomViewHeight) {
//                            height = zoomViewHeight;
//                        } else if (height >= zoomViewHeight * 2) {
//                            height = zoomViewHeight * 2;
//                        }
                        // 处理放大
                        mScaling = true;
                        lp.height = zoomViewHeight + distance;
                        scaleView.setLayoutParams(lp);

                        break; // 返回true表示已经完成触摸事件，不再处理
                }
                return false;
            }
        });

    }

    private void replyImage() {
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                zoomViewParams.height = zoomViewHeight;
                scaleView.setLayoutParams(zoomViewParams);
                mAnim_rv.scrollToPosition(0);
            }
        });
        anim.start();
    }
}
