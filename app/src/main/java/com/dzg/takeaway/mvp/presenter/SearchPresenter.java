package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.mvp.contract.SearchContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.HotBean;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.usecase.GetSearch;
import com.dzg.takeaway.ui.activity.SearchActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzhouguang on 2017/12/28.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private GetSearch mUsecase;
    private LocationClient mLocationClient;
    private RxAppCompatActivity mRxAppCompatActivity;

    public SearchPresenter(GetSearch usecase, LocationClient locationClient, RxAppCompatActivity rxAppCompatActivity) {
        this.mUsecase = usecase;
        this.mLocationClient = locationClient;
        this.mRxAppCompatActivity = rxAppCompatActivity;
    }

    @Override
    public void initDatas(SearchActivity.MyLocationListener myLocationListener) {
        Address address = (Address) ((RxAppCompatActivity)mRxAppCompatActivity).getIntent().getSerializableExtra("address");
        if (address != null){
            mView.showDatas(address);
            mLocationClient.registerLocationListener(myLocationListener);
            mLocationClient.start();
        }
    }

    @Override
    public void search(String keyword, double latitude, double longtitude) {
        mUsecase.execute(new GetSearch.RequestValues(keyword,latitude,longtitude,0)).getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxAppCompatActivity.<SearchRestaurant>bindUntilEvent(ActivityEvent.STOP))
                .subscribe(new Observer<SearchRestaurant>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchRestaurant searchRestaurant) {
                        mView.showSearch(searchRestaurant);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("心态蹦了", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

        @Override
    public void attachView(SearchContract.View view) {
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

    @Override
    public void onStop() {

    }

    public  ArrayList<HotBean> initRecommend() {
        ArrayList<HotBean> beans = new ArrayList<>();
        HotBean hotBean = new HotBean("猪杂汤");
        beans.add(hotBean);
        hotBean = new HotBean("牛肉面");
        beans.add(hotBean);
        hotBean = new HotBean("寿司");
        beans.add(hotBean);
        hotBean = new HotBean("热干面");
        beans.add(hotBean);
        hotBean = new HotBean("饼");
        beans.add(hotBean);
        hotBean = new HotBean("烧鸡");
        beans.add(hotBean);
        hotBean = new HotBean("粥");
        beans.add(hotBean);
        hotBean = new HotBean("木桶面");
        beans.add(hotBean);
        hotBean = new HotBean("炸鸡");
        beans.add(hotBean);
        hotBean = new HotBean("蛋糕");
        beans.add(hotBean);
        return beans;
    }
}
