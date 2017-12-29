package com.dzg.takeaway.injector.module;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.LocationActivityPresenter;
import com.dzg.takeaway.mvp.usecase.GetLocationAct;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class LocationActivityModule {
    @Provides
    @PerActivity
    LocationActivityPresenter provideLocationPresenter(RxAppCompatActivity rxActivity, GetLocationAct locationAct, LocationClient locationClient) {
        return new LocationActivityPresenter(rxActivity,locationAct,locationClient);
    }

    @Provides
    @PerActivity
    GetLocationAct provideGetLocationActivity(Repository repository) {
        return new GetLocationAct(repository);
    }


}
