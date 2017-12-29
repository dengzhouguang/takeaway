package com.dzg.takeaway.ui.view.refreshlibrary;

import android.view.View;

/**
 * 定义了拉动刷新的接口
 *
 * @param <T>
 */
public interface IPullToRefresh<T extends View> {
    
    /**
     * 设置当前下拉刷新是否可用
     * 
     * @param pullRefreshEnabled true表示可用，false表示不可用
     */
    public void setPullRefreshEnabled(boolean pullRefreshEnabled);

    /**
     * 判断当前下拉刷新是否可用
     * 
     * @return true如果可用，false不可用
     */
    public boolean isPullRefreshEnabled();

    /**
     * 设置刷新的监听器
     * 
     * @param refreshListener 监听器对象
     */
    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener<T> refreshListener);
    
    /**
     * 结束下拉刷新
     */
    public void onPullDownRefreshComplete();

    /**
     * 得到可刷新的View对象
     * 
     * @return 返回调用{@link #createRefreshableView(Context, AttributeSet)} 方法返回的对象
     */
    public T getRefreshableView();
    
    /**
     * 得到Header布局对象
     * 
     * @return Header布局对象
     */
    public LoadingLayout getHeaderLoadingLayout();

    /**
     * 设置最后更新的时间文本
     * 
     * @param label 文本
     */
    public void setLastUpdatedLabel(CharSequence label);
}
