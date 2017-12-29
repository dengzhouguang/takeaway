package com.dzg.takeaway.mvp.contract;

import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.presenter.base.BasePresenter;
import com.dzg.takeaway.mvp.view.BaseView;

import java.util.List;

public interface EvaluateContract {
    interface View extends BaseView {
        void showContent(RatingScore ratingScore);
        void showEvaluateCategory(List<EvaluateCategory>list);
        void showEvaluateSuccess(List<Evaluate> list);
        void showEvaluateComplete();
        void showError();
    }

    interface Presenter extends BasePresenter<EvaluateContract.View> {
        void initDatas(int id,double latitude,double longtitude);
        void initEvaluateCategory(int id);
        void initEvaluate(int id,int offset,int recordType);
    }
}