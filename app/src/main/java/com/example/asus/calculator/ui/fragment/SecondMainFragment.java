package com.example.asus.calculator.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.dao.CategoryDao;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Dish;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


//SUPER BAD CODE!!!!
public class SecondMainFragment extends Fragment {
    private static final String LOG_TAG = SecondMainFragment.class.getSimpleName();
    private Button btnCategories;
    private Button btnProducts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_second, container, false);

        btnCategories = (Button) rootView.findViewById(R.id.button_load_categories);
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Category, Long> dao = DBHelperFactory.getHelper().getCategoryDao();
                    load(dao, R.raw.insert_category);
                    Log.i(LOG_TAG, "Created category");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btnProducts = (Button) rootView.findViewById(R.id.button_load_products);
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Product, Long> dao = DBHelperFactory.getHelper().getProductDao();
                    load(dao, R.raw.insert_product);
                    Log.i(LOG_TAG, "Created product");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        Button getDishButton = (Button) rootView.findViewById(R.id.button_show_dish);
        final TextView tvDishText = (TextView) rootView.findViewById(R.id.tv_demonstrate_dish);
        getDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Dish, Long> dao = DBHelperFactory.getHelper().getDishDao();
                    CategoryDao categoryDao = DBHelperFactory.getHelper().getCategoryDao();
                    List<Dish> dishes = dao.queryForAll();
                    Log.i(LOG_TAG, "Amount of dishes: " + dishes.size());
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < dishes.size(); i++) {
                        Dish dish = dishes.get(i);
                        dao.refresh(dish);
                        String categoryName = categoryDao.queryForId(dish.getCategory().getId()).getName();
                        dish.getCategory().setName(categoryName);
                        builder.append(dish).append("\r\n");
                    }

                    tvDishText.setText(builder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private <T, ID> void load(Dao<T, ID> dao, int resId) throws SQLException {
        Log.i(LOG_TAG, "table name: " + dao.getTableName());
        int rows = dao.executeRaw("DELETE FROM " + dao.getTableName());
        Log.i(LOG_TAG, "rows deleted " + rows);
        InputStream is = getResources().openRawResource(resId);
        DataInputStream in = new DataInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        try {
            while ((strLine = br.readLine()) != null) {
                dao.updateRaw(strLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}