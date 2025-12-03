package com.fatec.lanchonetemobile;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.fatec.lanchonetemobile.config.AppBuilder;

import java.sql.SQLException;

public class LanchoneteApp extends Application {
    private AppBuilder appBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        appBuilder = new AppBuilder(getApplicationContext());
    }

    public AppBuilder getAppBuilder() {
        return appBuilder;
    }
}
