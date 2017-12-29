package com.dzg.takeaway.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dzg.takeaway.R;
import com.dzg.takeaway.ui.view.refreshlibrary.PullToRefreshBase;


/**
 */
public class PullToRefreshView extends PullToRefreshBase<FrameLayout> {
    /**
     * 判断是否可以刷新 or 是否拦截点击事件
     */
    private boolean canRefresh;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始为可刷新
        canRefresh = true;
    }

    /**
     * 判断是否可以刷新 or 是否拦截点击事件
     *
     * @param canRefresh true 拦截   false 不拦截
     */
    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    /**
     * 设置布局内容
     */
    @Override
    protected FrameLayout createRefreshableView(Context context, AttributeSet attrs) {
        FrameLayout frameLayout = new FrameLayout(context);
        View v = LayoutInflater.from(context).inflate(R.layout.layout_display, null);
        v.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.AT_MOST);
        frameLayout.addView(v);
        return frameLayout;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return canRefresh;
    }
}
