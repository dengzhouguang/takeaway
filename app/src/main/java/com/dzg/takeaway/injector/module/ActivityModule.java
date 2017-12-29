package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private final RxAppCompatActivity mActivity;

    public ActivityModule(RxAppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    public RxAppCompatActivity provideRxAppCompatActivity() {
        return mActivity;
    }
}
