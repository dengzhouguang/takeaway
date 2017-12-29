package com.dzg.takeaway;


import android.app.Application;

import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerApplicationComponent;
import com.dzg.takeaway.injector.module.ApplicationModule;
import com.dzg.takeaway.injector.module.NetworkModule;

/**
 * Created by dengzhouguang on 2017/11/26.
 */

public class App extends Application {
    public static App mInstance;
    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        injectSetup();
    }
    private void injectSetup() {
        mApplicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(this)).build();
    }
    public ApplicationComponent getApplicationComponent(){
        return  mApplicationComponent;
    }

    public static App getInstance() {
        return mInstance;
    }
}
