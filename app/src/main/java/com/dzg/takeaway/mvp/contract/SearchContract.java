package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.HotBean;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;
import com.dzg.takeaway.ui.activity.SearchActivity;

import java.util.ArrayList;

public interface SearchContract {
    interface View extends BaseView {
        void showDatas(Address address);
        void showSearch(SearchRestaurant searchRestaurant);

    }

    interface Presenter extends BasePresenter<SearchContract.View> {
        void  onStop();
        void initDatas(SearchActivity.MyLocationListener myLocationListener);
        void search(String keyword,double latitude,double longtitude);
        ArrayList<HotBean> initRecommend();
    }
}