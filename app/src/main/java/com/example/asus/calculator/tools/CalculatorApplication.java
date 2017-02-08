package com.example.asus.calculator.tools;

import android.app.Application;

import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.tools.db.DatabaseHelper;


public class CalculatorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelperFactory.setHelper(getApplicationContext(), DatabaseHelper.class);
    }

    @Override
    public void onTerminate() {
        DBHelperFactory.releaseHelper();
        super.onTerminate();
    }
}
