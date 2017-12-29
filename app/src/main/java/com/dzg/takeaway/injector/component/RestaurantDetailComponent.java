package com.dzg.takeaway.injector.component;


import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.RestaurantDetailModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.activity.RestaurantDetailActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {ActivityModule.class, RestaurantDetailModule.class})
public interface RestaurantDetailComponent {
    void inject(RestaurantDetailActivity activity);
}

