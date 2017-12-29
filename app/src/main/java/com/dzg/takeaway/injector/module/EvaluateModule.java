package com.dzg.takeaway.injector.module;

import com.dzg.takeaway.injector.scope.PerActivity;
import com.dzg.takeaway.mvp.presenter.EvaluatePresenter;
import com.dzg.takeaway.mvp.usecase.GetEvaluate;
import com.dzg.takeaway.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class EvaluateModule {
    @Provides
    @PerActivity
    EvaluatePresenter provideDisplayFragmentPresenter(RxFragment rxFragment, GetEvaluate getEvaluate) {
        return new EvaluatePresenter(rxFragment,getEvaluate);
    }

    @Provides
    @PerActivity
    GetEvaluate provideGetEvaluate(Repository repository) {
        return new GetEvaluate(repository);
    }


}
