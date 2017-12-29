package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;

import java.util.List;

public interface MoreAddressContract {
    interface View extends BaseView {
          void showCitys(List<String> list);
          void showSearch(List<Address> list);
    }

    interface Presenter extends BasePresenter<MoreAddressContract.View> {
        void saveAddress(Address address);
        void removeAddress(List<Address> list,String addressName);
        Object getAddressObject();
        void initCitys();
        void removeAll();
        void search(String keyword);
    }
}