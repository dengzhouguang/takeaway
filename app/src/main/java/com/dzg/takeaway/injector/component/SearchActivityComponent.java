package com.dzg.takeaway.injector.component;

import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.SearchActivityModule;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.ui.activity.SearchActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class},modules = {ActivityModule.class,SearchActivityModule.class})
public interface SearchActivityComponent {
    void inject(SearchActivity activity);
}
