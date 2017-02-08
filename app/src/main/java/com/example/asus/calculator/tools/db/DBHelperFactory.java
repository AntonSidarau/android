package com.example.asus.calculator.tools.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DBHelperFactory {
    private static DatabaseHelper helper;

    private DBHelperFactory() {

    }

    public static void setHelper(Context context, Class<? extends DatabaseHelper> openHelperClass) {
        DBHelperFactory.helper = OpenHelperManager.getHelper(context, openHelperClass);
    }

    public static DatabaseHelper getHelper() {
        return helper;
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        helper = null;
    }
}
