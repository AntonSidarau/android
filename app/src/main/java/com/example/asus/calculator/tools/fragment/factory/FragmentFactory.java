package com.example.asus.calculator.tools.fragment.factory;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.example.asus.calculator.R;
import com.example.asus.calculator.ui.fragment.CategoryFragment;
import com.example.asus.calculator.ui.fragment.MainFragment;
import com.example.asus.calculator.ui.fragment.NavigationFragment;
import com.example.asus.calculator.ui.fragment.ProductFragment;
import com.example.asus.calculator.ui.fragment.SecondMainFragment;

public class FragmentFactory {
    private static final String LOG_TAG = FragmentFactory.class.getSimpleName();
    private static FragmentFactory instance = new FragmentFactory();

    private SparseArray<Class<? extends Fragment>> array;

    public static FragmentFactory getInstance() {
        return instance;
    }

    private FragmentFactory() {
        array = new SparseArray<>();
        array.put(R.id.fragment_main, MainFragment.class);// FIXME: 25.09.2016 remove
        array.put(R.id.fragment_main_second, SecondMainFragment.class); // FIXME: 25.09.2016 remove
        array.put(R.id.fragment_navigation, NavigationFragment.class);
        array.put(R.id.fragment_category, CategoryFragment.class);
        array.put(R.id.fragment_product, ProductFragment.class);
    }

    public Fragment getFragment(int id) {
        Fragment fragment = null;
        try {
            fragment = array.get(id).newInstance();
        } catch (InstantiationException e) {
            Log.e(LOG_TAG, "getFragment()", e);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, "getFragment()", e);
        }
        return fragment;
    }
}
