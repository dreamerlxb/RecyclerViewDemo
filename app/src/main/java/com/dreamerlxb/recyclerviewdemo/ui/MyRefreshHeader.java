package com.dreamerlxb.recyclerviewdemo.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.dreamerlxb.recyclerviewdemo.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * TODO: document your custom view class.
 */
public class MyRefreshHeader extends FrameLayout implements RefreshHeader {
    private String animationName;

    private int mExampleColor = Color.RED;
    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private LottieAnimationView mLottieAnimationView;

    public MyRefreshHeader(Context context) {
        super(context);
        init(null, 0);
        initViews();
    }

    public MyRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        initViews();
    }

    public MyRefreshHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        initViews();
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyRefreshHeader, defStyle, 0);

        animationName = a.getString(R.styleable.MyRefreshHeader_animationName);

        mExampleColor = a.getColor(
                R.styleable.MyRefreshHeader_exampleColor,
                mExampleColor);

        mExampleDimension = a.getDimension(
                R.styleable.MyRefreshHeader_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.MyRefreshHeader_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.MyRefreshHeader_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initViews() {
        if (mLottieAnimationView == null) {
            mLottieAnimationView = new LottieAnimationView(getContext());
            FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200));
            lp.gravity = Gravity.CENTER;
            mLottieAnimationView.setLayoutParams(lp);
            mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
            mLottieAnimationView.setAnimation("spinner.json");
        }

        this.addView(mLottieAnimationView);
    }

    @NonNull
    @Override
    public View getView() {
        return mLottieAnimationView;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        mLottieAnimationView.playAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mLottieAnimationView.cancelAnimation();
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
