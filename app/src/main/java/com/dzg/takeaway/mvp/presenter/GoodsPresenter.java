package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.dzg.takeaway.mvp.contract.GoodsContract;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.usecase.GetGoods;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by dengzhouguang on 2017/12/29.
 */

public class GoodsPresenter implements GoodsContract.Presenter {
    private GoodsContract.View mView;
    private RxFragment mRxFragment;
    private GetGoods mUsecase;


    public GoodsPresenter(RxFragment rxFragment, GetGoods usecase) {
        this.mRxFragment = rxFragment;
        this.mUsecase = usecase;
    }

    @Override
    public void attachView(GoodsContract.View view) {
        this.mView=view;
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
    public void initRestaurantMenus(int id) {
        mUsecase.execute(new GetGoods.RequestValues(id)).getObservable()
                 .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mRxFragment.<List<RestaurantMenu>>bindUntilEvent(FragmentEvent.STOP))
                .subscribe(new Observer<List<RestaurantMenu>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RestaurantMenu> restaurantMenus) {
                        Log.e(TAG, "onNext: size:"+restaurantMenus.size()+"id: "+id);
                        mView.showRestaurantMenu(restaurantMenus);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onNext: size: error",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
