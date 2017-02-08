package com.example.asus.calculator.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.fragment.factory.FragmentFactory;
import com.example.asus.calculator.util.ModelUtil;

import java.io.Serializable;
import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.DISH_ACTIVITY_INTENT_EXTRA;
import static com.example.asus.calculator.util.MagicConstants.OLD_CHECKED_PRODUCTS;
import static com.example.asus.calculator.util.MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS;


public class DishActivity extends AppCompatActivity {
    private static final String LOG_TAG = DishActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        FragmentFactory factory = FragmentFactory.getInstance();
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra(DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_category);
        Fragment fragment = factory.getFragment(fragmentId);

        Bundle bundle = new Bundle();
        String key = SEARCH_ACTIVITY_CHECKED_PRODUCTS;
        if (intent.getSerializableExtra(key) != null) {
            Log.d(LOG_TAG, "Received checked list");
            List<ProductModel> checkedList = (List<ProductModel>) intent.getSerializableExtra(key);
            if (intent.getSerializableExtra(OLD_CHECKED_PRODUCTS) != null) {
                List<ProductModel> oldList = (List<ProductModel>) intent.getSerializableExtra(OLD_CHECKED_PRODUCTS);
                bundle.putSerializable(key, (Serializable) ModelUtil.getComparedList(oldList, checkedList));
            } else {
                Log.w(LOG_TAG, "old checked products is null");
                bundle.putSerializable(key, intent.getSerializableExtra(key));
            }
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_top, factory.getFragment(R.id.fragment_navigation));
        transaction.replace(R.id.frame_low, fragment);
        transaction.commit();
    }
}
