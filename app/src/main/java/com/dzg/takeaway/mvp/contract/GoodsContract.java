package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;

import java.util.List;

public interface GoodsContract {
    interface View extends BaseView {
        void showRestaurantMenu(List<RestaurantMenu> list);
    }

    interface Presenter extends BasePresenter<GoodsContract.View> {
        void initRestaurantMenus(int id);
    }
}