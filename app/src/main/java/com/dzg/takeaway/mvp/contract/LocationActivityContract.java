package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;
import com.dzg.takeaway.ui.activity.LocationActivity;

import java.util.ArrayList;
import java.util.List;

public interface LocationActivityContract {
    interface View extends BaseView {
           void showAddress(List<Address> addresses);
           void searchSuccess(List<Address> addresses);
           void showCitys(ArrayList<String> citys);
    }

    interface Presenter extends BasePresenter<LocationActivityContract.View> {
        void  restart();
        void  onStop();
        void initLocation(LocationActivity.MyLocationListener locationListener);
        void initDatas(String addr);
        void search(String keyword);
        void  initCityDatas();
    }
}