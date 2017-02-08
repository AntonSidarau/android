package com.example.asus.calculator.ui.activity;

import android.app.LoaderManager;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.widget.SearchView;

public interface BunchOfListeners extends NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {

}
