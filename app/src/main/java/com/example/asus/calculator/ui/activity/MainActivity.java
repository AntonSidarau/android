package com.example.asus.calculator.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.calculator.R;
import com.example.asus.calculator.tools.adapter.PagerAdapter;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.tools.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = DBHelperFactory.getHelper();
        databaseHelper.checkDatabaseVersion();
        /* Settings for sqlscout*/
        //SqlScoutServer.create(this, getPackageName());
        /**/

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_main_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_main_second_name));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setText(R.string.main_tab_name);
        //tabLayout.getTabAt(1).setText(R.string.main_second_tab_name);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
