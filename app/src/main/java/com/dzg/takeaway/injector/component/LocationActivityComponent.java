package com.dzg.takeaway.injector.component;

import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.LocationActivityModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.activity.LocationActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {ActivityModule.class,LocationActivityModule.class})
public interface LocationActivityComponent {
    void inject(LocationActivity activity);
}
