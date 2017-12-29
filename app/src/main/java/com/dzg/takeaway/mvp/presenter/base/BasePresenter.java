package com.dzg.takeaway.mvp.presenter.base;


import com.dzg.takeaway.mvp.view.BaseView;

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);
    void onDestory();
    void onPause();
    void onRestart();
}
