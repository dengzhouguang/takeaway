package com.dzg.takeaway.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public abstract class BaseFragment extends RxFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this,inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initDatas();
    }
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initDatas();

    protected View getRootView() {
        return null;
    }


}
