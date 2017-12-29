package com.dzg.takeaway.injector.component;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.App;
import com.dzg.takeaway.injector.module.ApplicationModule;
import com.dzg.takeaway.injector.module.NetworkModule;
import com.dzg.takeaway.injector.scope.PerApplication;
import com.dzg.takeaway.repository.Repository;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    Application application();

    App app();

    Repository repository();

    LocationClient locationClient();
}
