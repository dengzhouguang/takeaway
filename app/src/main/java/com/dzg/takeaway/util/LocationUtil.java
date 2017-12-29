package com.dzg.takeaway.util;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dzg.takeaway.App;

/**
 * Created by dengzhouguang on 2017/12/28.
 */

public class LocationUtil {
    private static LocationClient mLocationClient;

    public static LocationClient newLocationClient() {
        if (mLocationClient==null)
        synchronized (LocationUtil.class){
            if (mLocationClient==null){
                mLocationClient = new LocationClient(App.getInstance());
                mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {

                    }
                });
                LocationClientOption option = new LocationClientOption();
                option.setIsNeedAddress(true);
                option.setScanSpan(1000);
                option.setOpenGps(true);
                option.setIsNeedLocationPoiList(true);
                mLocationClient.setLocOption(option);
            }
        }
        return mLocationClient;
    }
}
