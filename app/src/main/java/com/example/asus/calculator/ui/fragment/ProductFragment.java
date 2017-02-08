package com.example.asus.calculator.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.dao.CategoryDao;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Dish;
import com.example.asus.calculator.tools.adapter.ProductPickedAdapter;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.ui.activity.DishActivity;
import com.j256.ormlite.dao.Dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.DISH_ACTIVITY_INTENT_EXTRA;
import static com.example.asus.calculator.util.MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS;


public class ProductFragment extends ListFragment implements View.OnClickListener {
    private static final String LOG_TAG = ProductFragment.class.getSimpleName();

    private Button btnAdd;
    private List<ProductModel> productList;
    private ProductPickedAdapter adapter;

    public ProductFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        productList = (List<ProductModel>) bundle.getSerializable(SEARCH_ACTIVITY_CHECKED_PRODUCTS);
        if (productList != null) {
            Log.i(LOG_TAG, "amount of products: " + productList.size());
        }

        adapter = new ProductPickedAdapter(getContext(), productList);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        btnAdd = (Button) rootView.findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAdd.getId()) {
            Intent intent = new Intent(getActivity(), DishActivity.class);
            intent.putExtra(DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_category);
            intent.putExtra(SEARCH_ACTIVITY_CHECKED_PRODUCTS, (Serializable) productList);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_product, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_remove_products:
                adapter.removeChecked();
                adapter.notifyDataSetChanged();
                break;

            case R.id.menu_item_save_dish:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Save dish");
                Context context = getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText categoryBox = new EditText(context);
                categoryBox.setHint("Name of category");
                categoryBox.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(categoryBox);

                final EditText dishBox = new EditText(context);
                dishBox.setHint("Name of dish");
                dishBox.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(dishBox);
                builder.setView(layout);

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String categoryName = categoryBox.getText().toString();
                        String dishName = dishBox.getText().toString();
                        saveDish(categoryName, dishName);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveDish(String categoryName, String dishName) {
        try {
            Dao<Dish, Long> dishDao = DBHelperFactory.getHelper().getDishDao();
            CategoryDao categoryDao = DBHelperFactory.getHelper().getCategoryDao();

            Log.d(LOG_TAG, "CATEGORY: " + categoryName);
            List<Category> listCategory = categoryDao.queryForEq(Category.NAME, categoryName);
            Log.d(LOG_TAG, "CATEGORY AMOUNT: " + listCategory.size());
            Category category = listCategory.get(0);


            Dish dish = new Dish();
            dish.setName(dishName);
            dish.setCategory(category);
            dishDao.create(dish);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveParts() {

    }
}
