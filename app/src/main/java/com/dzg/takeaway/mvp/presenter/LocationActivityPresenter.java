package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.mvp.contract.LocationActivityContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.usecase.GetLocationAct;
import com.dzg.takeaway.ui.activity.LocationActivity;
import com.dzg.takeaway.util.SQLHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LocationActivityPresenter implements LocationActivityContract.Presenter {
    private LocationActivityContract.View mView;
    private RxAppCompatActivity mRxActivity;
    private GetLocationAct mUescase;
    private LocationClient mLocationClient;

    public LocationActivityPresenter(RxAppCompatActivity rxActivity, GetLocationAct uescase, LocationClient locationClient) {
        this.mRxActivity = rxActivity;
        this.mUescase = uescase;
        this.mLocationClient = locationClient;
    }

    @Override
    public void attachView(LocationActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onDestory() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onPause() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onRestart() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void restart() {
        mLocationClient.restart();
    }

    @Override
    public void onStop() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void initLocation(LocationActivity.MyLocationListener locationListener) {
        mLocationClient.registerLocationListener(locationListener);
        mLocationClient.start();
    }

    @Override
    public void initDatas(String addr) {
        mUescase.execute(new GetLocationAct.RequestValues(addr, 50)).getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxActivity.<List<Address>>bindUntilEvent(ActivityEvent.STOP))
                .subscribe(new Observer<List<Address>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Address> addresses) {
                        mView.showAddress(addresses);
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
    public void search(String keyword) {
            mUescase.execute(new GetLocationAct.RequestValues(keyword,20)).getObservable()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(mRxActivity.<List<Address>>bindUntilEvent(ActivityEvent.STOP))
                    .subscribe(new Observer<List<Address>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Address> addresses) {
                            mView.searchSuccess(addresses);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("initDataTest", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.e("initDataTest", "onComplete");
                        }
                    });

    }

    @Override
    public void initCityDatas() {
        mView.showCitys(SQLHelper.getCityList(mRxActivity));
    }
}
