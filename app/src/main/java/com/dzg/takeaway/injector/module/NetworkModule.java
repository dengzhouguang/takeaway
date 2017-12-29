package com.dzg.takeaway.injector.module;


import com.baidu.location.LocationClient;
import com.dzg.takeaway.App;
import com.dzg.takeaway.injector.scope.PerApplication;
import com.dzg.takeaway.repository.Repository;
import com.dzg.takeaway.repository.RepositoryImpl;
import com.dzg.takeaway.util.LocationUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    private final App mApp;

    public NetworkModule(App app) {
        mApp = app;
    }

    @Provides
    @PerApplication
    Repository provideRepository() {
        return RepositoryImpl.newInstance();
    }

    @Provides
    @PerApplication
    LocationClient provideLocationClient(){
        return LocationUtil.newLocationClient();
    }
}
