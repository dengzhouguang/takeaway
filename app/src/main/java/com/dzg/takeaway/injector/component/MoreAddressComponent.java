package com.dzg.takeaway.injector.component;


import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.MoreAddressModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.activity.MoreAddrssActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {ActivityModule.class, MoreAddressModule.class})
public interface MoreAddressComponent {
    void inject(MoreAddrssActivity activity);
}

