package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.GoodsPresenter;
import com.dzg.takeaway.mvp.usecase.GetGoods;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class GoodsModule {
    @Provides
    @PerActivity
    GoodsPresenter provideDisplayFragmentPresenter(RxFragment rxFragment, GetGoods getGoods) {
        return new GoodsPresenter(rxFragment,getGoods);
    }

    @Provides
    @PerActivity
    GetGoods provideGoods(Repository repository) {
        return new GetGoods(repository);
    }


}
