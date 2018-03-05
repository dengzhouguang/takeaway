package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.dzg.takeaway.mvp.contract.RestaurantDetailContract;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.usecase.GetRestaurantDetail;
import com.dzg.takeaway.ui.activity.RestaurantDetailActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzhouguang on 2017/12/28.
 */

public class RestaurantDetailPresenter implements RestaurantDetailContract.Presenter {
    private GetRestaurantDetail mUsecase;
    private RxAppCompatActivity mRxAppCompatActivity;
    private RestaurantDetailContract.View mView;

    public RestaurantDetailPresenter(GetRestaurantDetail usecase, RxAppCompatActivity rxAppCompatActivity){
           this.mUsecase=usecase;
           this.mRxAppCompatActivity=rxAppCompatActivity;
    }

    @Override
    public void getRestaurant() {
        Restaurant restaurant= (Restaurant) ((RestaurantDetailActivity)mRxAppCompatActivity).getIntent().getExtras().getSerializable("restaurant");
        initDatas(restaurant.getId());
    }

    @Override
    public void initDatas(int id) {
        mUsecase.execute(new GetRestaurantDetail.RequestValues(id)).getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                 .compose(mRxAppCompatActivity.<RestaurantDetail>bindUntilEvent(ActivityEvent.STOP))
                .subscribe(new Observer<RestaurantDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantDetail restaurantDetail) {
                        mView.showDatas(restaurantDetail);
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
    public void attachView(RestaurantDetailContract.View view) {
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
