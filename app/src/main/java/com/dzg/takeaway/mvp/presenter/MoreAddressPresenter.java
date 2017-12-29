package com.dzg.takeaway.mvp.presenter;

import android.util.Log;

import com.dzg.takeaway.App;
import com.dzg.takeaway.mvp.contract.MoreAddressContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.usecase.GetMoreAddress;
import com.dzg.takeaway.util.SPUtils;
import com.dzg.takeaway.util.SQLHelper;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dengzhouguang on 2017/12/28.
 */

public class MoreAddressPresenter implements MoreAddressContract.Presenter {
    private GetMoreAddress mUsecase;
    private MoreAddressContract.View mView;
    private RxAppCompatActivity mRxAppCompatActivity;
    public MoreAddressPresenter(GetMoreAddress getMoreAddress,RxAppCompatActivity rxAppCompatActivity){
        mUsecase=getMoreAddress;
        mRxAppCompatActivity=rxAppCompatActivity;
    }
    @Override
    public void attachView(MoreAddressContract.View view) {
        mView=view;
    }



    @Override
    public void saveAddress(Address address) {
        Object object = SPUtils.getObjectFromShare("addresses");
        List<Address> addresses;
        if (object != null) {
            addresses = (List<Address>) object;
            for (Address address1 : addresses) {
                if (address1.getName().equals(address.getName())) {
                    return;
                }
            }
        } else
            addresses = new ArrayList<>();
        addresses.add(0, address);
        SPUtils.putObjectToShare("addresses", addresses);
    }

    @Override
    public void removeAddress(List<Address> list, String addressName) {
        for (Address address : list) {
            if (address.getName().equals(addressName)) {
                list.remove(address);
            }
        }
        SPUtils.putObjectToShare("addresses", list);
    }

    @Override
    public Object getAddressObject() {
        return SPUtils.getObjectFromShare("addresses");

    }

    @Override
    public void initCitys() {
        mView.showCitys(SQLHelper.getCityList(App.getInstance()));
    }

    @Override
    public void removeAll() {
        SPUtils.remove("addresses");

    }

    @Override
    public void search(String keyword) {
     mUsecase.execute(new GetMoreAddress.RequestValues(keyword,20)).getObservable()
             .subscribeOn(Schedulers.newThread())
             .observeOn(AndroidSchedulers.mainThread())
             .compose(mRxAppCompatActivity.<List<Address>>bindUntilEvent(ActivityEvent.STOP))
             .subscribe(new Observer<List<Address>>() {
                 @Override
                 public void onSubscribe(Disposable d) {

                 }

                 @Override
                 public void onNext(List<Address> addresses) {
                     mView.showSearch(addresses);
                     Log.e("initDataTest", "onNext:" + addresses.size());
                 }

                 @Override
                 public void onError(Throwable e) {
                     Log.e("initDataTest", e.getMessage());
                 }

                 @Override
                 public void onComplete() {
                     Log.e("initDataTest", "onComplete");
                 }
             });
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
}
