package com.example.asus.calculator.tools;

import android.app.Application;

import com.example.asus.calculator.tools.dagger.AppComponent;
import com.example.asus.calculator.tools.dagger.DaggerAppComponent;
import com.example.asus.calculator.tools.dagger.modules.AdapterModule;
import com.example.asus.calculator.tools.dagger.modules.ServiceModule;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.tools.db.DatabaseHelper;


public class CalculatorApplication extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = this.buildComponent();
        DBHelperFactory.setHelper(getApplicationContext(), DatabaseHelper.class);
    }

    @Override
    public void onTerminate() {
        DBHelperFactory.releaseHelper();
        super.onTerminate();
    }

    public static AppComponent getComponent() {
        return component;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .adapterModule(new AdapterModule())
                .serviceModule(new ServiceModule())
                .build();
    }
}
