package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.dzg.takeaway.mvp.contract.EvaluateContract;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.usecase.GetEvaluate;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzhouguang on 2017/12/29.
 */

public class EvaluatePresenter implements EvaluateContract.Presenter{
    private EvaluateContract.View mView;
    private RxFragment mRxFragment;
    private GetEvaluate mUsecase;



    public EvaluatePresenter(RxFragment rxFragment,GetEvaluate usecase){
    this.mRxFragment=rxFragment;
    this.mUsecase=usecase;
    }


    @Override
    public void initDatas(int id, double latitude, double longtitude) {
     mUsecase.execute(new GetEvaluate.RequestValues(GetEvaluate.ACTION_RATING_SCORE,id,latitude,longtitude)).getRatingScoreObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<RatingScore>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<RatingScore>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RatingScore ratingScore) {
                        mView.showContent(ratingScore);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initEvaluateCategory(int id) {
        mUsecase.execute(new GetEvaluate.RequestValues(GetEvaluate.ACTION_EVALUATE_CATEGORY,id)).getEvaluateCategoryObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<EvaluateCategory>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<EvaluateCategory>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EvaluateCategory> evaluateCategories) {
                        mView.showEvaluateCategory(evaluateCategories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initEvaluate(int id, int offset, int recordType) {
        mUsecase.execute(new GetEvaluate.RequestValues(GetEvaluate.ACTION_EVALUATE,id,offset,recordType)).getEvaluateObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<Evaluate>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<Evaluate>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Evaluate> evaluates) {
                       mView.showEvaluateSuccess(evaluates);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                    }

                    @Override
                    public void onComplete() {
                        mView.showEvaluateComplete();
                    }
                });
    }

    @Override
    public void attachView(EvaluateContract.View view) {
        mView=view;
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRestart() {

    }


}
