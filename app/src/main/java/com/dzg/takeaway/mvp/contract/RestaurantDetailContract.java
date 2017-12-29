package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;

public interface RestaurantDetailContract {
    interface View extends BaseView {
     void showDatas(RestaurantDetail restaurantDetail);
    }

    interface Presenter extends BasePresenter<RestaurantDetailContract.View> {
     void getRestaurant();
     void initDatas(int id);
    }
}