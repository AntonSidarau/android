package com.example.asus.calculator.tools;

import android.app.Application;
import android.util.Log;

import com.example.asus.calculator.tools.dagger.AppComponent;
import com.example.asus.calculator.tools.dagger.DaggerAppComponent;
import com.example.asus.calculator.tools.dagger.RecycleViewComponent;
import com.example.asus.calculator.tools.dagger.RetroUsersComponent;
import com.example.asus.calculator.tools.dagger.modules.AdapterModule;
import com.example.asus.calculator.tools.dagger.modules.ServiceModule;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.tools.db.DatabaseHelper;


public class CalculatorApplication extends Application {
    private static final String TAG = CalculatorApplication.class.getSimpleName();
    private static CalculatorApplication app;

    private AppComponent appComponent;
    private RecycleViewComponent recycleViewComponent;
    private RetroUsersComponent retroUsersComponent;

    public static CalculatorApplication get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.create();
        Log.d(TAG, "onCreate: appComponent" + appComponent);
        DBHelperFactory.setHelper(getApplicationContext(), DatabaseHelper.class);
    }

    @Override
    public void onTerminate() {
        DBHelperFactory.releaseHelper();
        super.onTerminate();
    }

    public RecycleViewComponent getRecycleViewComponent() {
        if (recycleViewComponent == null) {
            recycleViewComponent = appComponent.addRecycleViewComponent(new AdapterModule());
        }
        return recycleViewComponent;
    }

    public void clearRecycleViewComponent() {
        recycleViewComponent = null;
    }

    public RetroUsersComponent getRetroUsersComponent() {
        if (retroUsersComponent == null) {
            retroUsersComponent = recycleViewComponent.addRetroUserComponent(new ServiceModule());
        }
        return retroUsersComponent;
    }

    public void clearRetroUserComponent() {
        retroUsersComponent = null;
    }
}
