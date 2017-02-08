package com.example.asus.calculator.tools.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.asus.calculator.ui.fragment.MainFragment;
import com.example.asus.calculator.ui.fragment.SecondMainFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final int TAB_COUNT = 2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();

            case 1:
                return new SecondMainFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
