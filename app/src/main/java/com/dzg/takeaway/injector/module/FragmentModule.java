package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {
    private final RxFragment mRxFragment;

    public FragmentModule(RxFragment rxFragment) {
        this.mRxFragment = rxFragment;
    }

    @Provides
    @PerActivity
    public RxFragment provideRxFragment() {
        return mRxFragment;
    }
}
