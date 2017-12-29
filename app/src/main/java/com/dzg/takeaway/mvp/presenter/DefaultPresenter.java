/*
package com.dzg.takeaway.mvp.presenter;

import com.baidu.location.LocationClient;
import com.dzg.takeaway.mvp.contract.LocationActivityContract;
import com.dzg.takeaway.mvp.usecase.GetLocationAct;
import com.trello.rxlifecycle2.components.support.RxFragment;

*/
/**
 * Created by dengzhouguang on 2017/12/28.
 *//*


public class DefaultPresenter implements LocationActivityContract.Presenter {
    private LocationActivityContract.View mView;
    private RxFragment mRxFragment;
    private GetLocationAct mUescase;
    private LocationClient mLocationClient;

    public DefaultPresenter(RxFragment mRxFragment, GetLocationAct mUescase, LocationClient mLocationClient) {
        this.mRxFragment = mRxFragment;
        this.mUescase = mUescase;
        this.mLocationClient = mLocationClient;
    }

    @Override
    public void attachView(LocationActivityContract.View view) {
    mView=view;
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
        if (mLocationClient!=null)
            mLocationClient.stop();
    }

    @Override
    public void onStop() {
        if (mLocationClient!=null)
            mLocationClient.stop();
    }
}
*/
