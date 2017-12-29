package com.dzg.takeaway.injector.component;

import com.dzg.takeaway.injector.module.EvaluateModule;
import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.fragment.EvaluateFragment;

import dagger.Component;


@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {FragmentModule.class,EvaluateModule.class})
public interface EvaluateComponent {
    void inject(EvaluateFragment fragment);
}
