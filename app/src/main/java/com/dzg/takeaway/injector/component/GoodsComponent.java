package com.dzg.takeaway.injector.component;

import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.injector.module.GoodsModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.fragment.GoodsFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {FragmentModule.class,GoodsModule.class})
public interface GoodsComponent {
    void inject(GoodsFragment fragment);
}
