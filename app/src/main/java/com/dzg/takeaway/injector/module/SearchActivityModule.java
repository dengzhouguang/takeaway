package com.dzg.takeaway.injector.module;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.SearchPresenter;
import com.dzg.takeaway.mvp.usecase.GetSearch;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class SearchActivityModule {
    @Provides
    @PerActivity
    SearchPresenter provideActivityPresenter(RxAppCompatActivity rxActivity, GetSearch getSearch, LocationClient locationClient) {
        return new SearchPresenter(getSearch,locationClient,rxActivity);
    }

    @Provides
    @PerActivity
    GetSearch provideGetSearchActivity(Repository repository) {
        return new GetSearch(repository);
    }


}
