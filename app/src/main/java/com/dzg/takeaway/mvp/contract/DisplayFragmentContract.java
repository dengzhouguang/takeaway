package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Category;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;
import com.dzg.takeaway.ui.fragment.DisplayFragment;

import java.util.List;

public interface DisplayFragmentContract {
    interface View extends BaseView {
        void showCategory(List<Category> list);
        void showUpdatesSuccess(List<Restaurant> restaurants);
        void showUpdatesError(Throwable e);
        void showUpdatesCompleter();
        void showInitSuccess(List<Address> addresses);
        void showInitError(Throwable e);
        void showRestaurantsSuccess(List<Restaurant> list);
        void showRestaurantsError(Throwable throwable);
        void showWeather(Weather weather);
    }

    interface Presenter extends BasePresenter<DisplayFragmentContract.View> {
        void  getCategory();
        void  updates(Address address);
        void  initDates(String address);
        void  getRestaurants(Address address, int offset);
        void  getWeather(double latitude,double longtitude);
        void  getWeatherDetail(String id);
        void  initLocation(DisplayFragment.MyLocationListener bdAbstractLocationListener);
        void  onStop();
    }
}