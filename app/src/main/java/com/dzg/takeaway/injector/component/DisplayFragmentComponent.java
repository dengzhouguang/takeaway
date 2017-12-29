package com.dzg.takeaway.injector.component;

import com.dzg.takeaway.injector.module.DisplayFragmentModule;
import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.fragment.DisplayFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {FragmentModule.class,DisplayFragmentModule.class})
public interface DisplayFragmentComponent {
    void inject(DisplayFragment fragment);
}
