package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;

public interface DefaultContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<DefaultContract.View> {
        void  onStop();
    }
}