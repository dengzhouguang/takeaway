package com.dzg.takeaway.injector.module;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.DisplayFragmentPresenter;
import com.dzg.takeaway.mvp.usecase.GetDisplayFrag;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class DisplayFragmentModule {
    @Provides
    @PerActivity
    DisplayFragmentPresenter provideDisplayFragmentPresenter(RxFragment rxFragment, GetDisplayFrag getDisplayFragment, LocationClient locationClient) {
        return new DisplayFragmentPresenter(rxFragment,getDisplayFragment,locationClient);
    }

    @Provides
    @PerActivity
    GetDisplayFrag provideGetDisplayFragment(Repository repository) {
        return new GetDisplayFrag(repository);
    }


}
