package com.dzg.takeaway.injector.module;

import android.app.Application;

import com.dzg.takeaway.App;
import com.dzg.takeaway.injector.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final App app;

    public ApplicationModule(App application) {
        app = application;
    }

    @Provides
    @PerApplication
    public Application provideApplication() {
        return app;
    }

    @Provides
    @PerApplication
    public App provideApp() {
        return app;
    }
}
