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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dreamerlxb.recyclerviewdemo.adapter.ScaleHeaderAdapter;

public class ScaleHeaderActivity extends AppCompatActivity {

    private RecyclerView mAnim_rv;
    private View scaleView;
    private DisplayMetrics metric;
//    private boolean mScaling = false;
    private float mFirstPosition = 0;

    private ScaleHeaderAdapter mTransAdapter;
    private LinearLayoutManager mLinearLayoutManager;

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
                metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                Log.i("==metric==", metric.toString());
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scaleView.getLayoutParams();
                Log.i("==lp==", "width: " + lp.width + "    height: " + lp.height);
                lp.width = metric.widthPixels;
                lp.height = metric.widthPixels * 9 / 16;
                scaleView.setLayoutParams(lp);
            }
        });

        mAnim_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scaleView.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("======", "action down");
                        if (mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                            mFirstPosition = event.getY();
                        } else {
                            mFirstPosition = 0;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("======", " action up");
                        replyImage();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("======", "action move");
                        int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                        if (distance < 0) {
                            break;
                        }
                        // 处理放大
                        lp.width = metric.widthPixels + distance;
                        lp.height = (metric.widthPixels + distance) * 9 / 16;
                        scaleView.setLayoutParams(lp);
                        break; // 返回true表示已经完成触摸事件，不再处理
                }
                return false;
            }
        });

    }


    private void replyImage() {
        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) scaleView.getLayoutParams();
        final float w = scaleView.getLayoutParams().width;// 图片当前宽度
        final float h = scaleView.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 16;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                scaleView.setLayoutParams(lp);
            }
        });
        anim.start();
    }
}
