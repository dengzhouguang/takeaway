package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.mvp.contract.DisplayFragmentContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.model.WeatherCity;
import com.dzg.takeaway.mvp.usecase.GetDisplayFrag;
import com.dzg.takeaway.ui.fragment.DisplayFragment;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class DisplayFragmentPresenter implements DisplayFragmentContract.Presenter {
    private DisplayFragmentContract.View mView;
    private GetDisplayFrag mUseCase;
    private RxFragment mRxFragment;
    private LocationClient mLocationClient;

    public DisplayFragmentPresenter(RxFragment rxFragment, GetDisplayFrag useCase,LocationClient locationClient) {
        this.mUseCase = useCase;
        this.mRxFragment = rxFragment;
        this.mLocationClient=locationClient;
    }

    @Override
    public void attachView(DisplayFragmentContract.View view) {
        this.mView = view;
    }


    @Override
    public void getCategory() {
        mView.showCategory(mUseCase.getCategorys());
    }

    @Override
    public void updates(Address address) {
        mUseCase.execute(new GetDisplayFrag.RequestValues(GetDisplayFrag.ACTION_UPDATE,address, 0)).getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<Restaurant>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<Restaurant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        mView.showUpdatesSuccess(restaurants);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showUpdatesError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.showUpdatesCompleter();
                    }
                });
    }

    @Override
    public void initDates(String address) {
        mUseCase.execute(new GetDisplayFrag.RequestValues(GetDisplayFrag.ACTION_INIT,address,20)).getAddressObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<Address>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<Address>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Address> addresses) {
                        mView.showInitSuccess(addresses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showInitError(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("AndroidTest", "onComplete");
                    }
                });
    }

    @Override
    public void getRestaurants(Address address, int offset) {
        mUseCase.execute(new GetDisplayFrag.RequestValues(GetDisplayFrag.ACTION_OFFSET,address,offset)).getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<Restaurant>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<Restaurant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                           mView.showRestaurantsSuccess(restaurants);
                    }

                    @Override
                    public void onError(Throwable e) {
                           mView.showRestaurantsError(e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getWeather(double latitude, double longtitude) {
        mUseCase.execute(new GetDisplayFrag.RequestValues(GetDisplayFrag.ACTION_WEATHER_CITY,latitude + ":" + longtitude)).getWeatherCityObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<WeatherCity>bindUntilEvent(FragmentEvent.STOP))
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable
                                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                        if (throwable instanceof UnknownHostException) {
                                            return Observable.error(throwable);
                                        } else {
                                            return Observable.timer(5, TimeUnit.SECONDS);
                                        }
                                    }
                                });
                    }
                }).subscribe(new Observer<WeatherCity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeatherCity weatherCity) {
                if (weatherCity != null&&weatherCity.getResults()!=null) {
                    getWeatherDetail(weatherCity.getResults().get(0).getId());
                }
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
    public void getWeatherDetail(String id) {
        mUseCase.execute(new GetDisplayFrag.RequestValues(GetDisplayFrag.ACTION_WEATHER, id)).getWeatherObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<Weather>bindUntilEvent(FragmentEvent.STOP))
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable
                                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                        if (throwable instanceof UnknownHostException) {
                                            return Observable.error(throwable);
                                        } else {
                                            return Observable.timer(5, TimeUnit.SECONDS);
                                        }
                                    }
                                });
                    }
                }).subscribe(new Observer<Weather>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Weather weather) {
                mView.showWeather(weather);
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
    public void initLocation(DisplayFragment.MyLocationListener bdAbstractLocationListener) {
        mLocationClient.registerLocationListener(bdAbstractLocationListener);
        mLocationClient.start();
    }

    @Override
    public void onStop() {
        if (mLocationClient!=null)
            mLocationClient.stop();
    }


    @Override
    public void onDestory() {
        if (mLocationClient!=null)
       mLocationClient.stop();
    }

    @Override
    public void onPause() {
        if (mLocationClient!=null)
        mLocationClient.stop();
    }

    @Override
    public void onRestart() {

    }


}
