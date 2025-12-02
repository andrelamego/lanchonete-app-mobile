package com.fatec.lanchonetemobile;

import android.app.Application;

import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class LanchoneteApp extends Application {
    private AppBuilder appBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        appBuilder = new AppBuilder(getApplicationContext());
    }

    public AppBuilder getAppBuilder() {
        return appBuilder;
    }
}
