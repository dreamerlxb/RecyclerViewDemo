package com.dreamerlxb.recyclerviewdemo.rv.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamerlxb.recyclerviewdemo.R;
import com.dreamerlxb.recyclerviewdemo.rv.adapter.ScaleHeaderAdapter;

public class ScaleHeaderActivity extends AppCompatActivity {

    private RecyclerView mAnim_rv;
    private View mScaleView;
    private float mFirstPosition = 0;
    private int mDistance;//记录上次下滑的距离

    private ScaleHeaderAdapter mTransAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mScaling = false;

    @SuppressLint("ClickableViewAccessibility")
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
                mScaleView = view;
            }
        });


        mAnim_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mScaleView.getLayoutParams();
//                Log.i("ScaleView：", " width: " + lp.width + "   height: " + lp.height);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mScaling) {
                            replyImage();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mScaling) {
                            int p = mLinearLayoutManager.findFirstVisibleItemPosition();
                            if (p == 0 && mLinearLayoutManager.findViewByPosition(p).getTop() == 0) {
                                mFirstPosition = event.getY();
                            } else {
                                break;
                            }
                        }

                        int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
//                        Log.i("====", "   " + distance);
                        if (distance < 0) {
                            break;
                        }

                        // 处理放大
                        mScaling = true;
                        lp.height = lp.height + (distance - mDistance);
                        mScaleView.setLayoutParams(lp);
                        mDistance = distance;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void replyImage() {
        // 设置动画
        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mScaleView.getLayoutParams();
        ValueAnimator anim = ValueAnimator.ofInt(lp.height, lp.height - mDistance).setDuration(1000);
//        Log.i("==120= ", "  lp.height - mDistance = " + (lp.height - mDistance) + "   lp.height = " + lp.height);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int d = (int) animation.getAnimatedValue();
//                Log.i("==121= ", "  d = " + d + "  lp.height = " + lp.height);
                lp.height = d;
                mScaleView.setLayoutParams(lp);
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后，将下滑距离置为空
                mScaling = false;
                mDistance = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }
}
