package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.MoreAddressPresenter;
import com.dzg.takeaway.mvp.usecase.GetMoreAddress;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class MoreAddressModule {
    @Provides
    @PerActivity
    MoreAddressPresenter provideMoreAddressPresenter(RxAppCompatActivity rxActivity,GetMoreAddress getMoreAddress) {
        return new MoreAddressPresenter(getMoreAddress,rxActivity);
    }

    @Provides
    @PerActivity
    GetMoreAddress provideGetMoreAddressActivity(Repository repository) {
        return new GetMoreAddress(repository);
    }


}
