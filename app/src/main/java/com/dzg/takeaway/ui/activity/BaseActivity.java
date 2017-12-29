package com.dzg.takeaway.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initDatas();
    }
    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initDatas();





}
