package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.RestaurantDetailPresenter;
import com.dzg.takeaway.mvp.usecase.GetRestaurantDetail;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class RestaurantDetailModule {
    @Provides
    @PerActivity
    RestaurantDetailPresenter provideMoreAddressPresenter(RxAppCompatActivity rxActivity, GetRestaurantDetail getRestaurantDetail) {
        return new RestaurantDetailPresenter(getRestaurantDetail,rxActivity);
    }

    @Provides
    @PerActivity
    GetRestaurantDetail provideGetMoreAddressActivity(Repository repository) {
        return new GetRestaurantDetail(repository);
    }


}
