package com.example.asus.calculator.ui.activity;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.adapter.ProductAdapter;
import com.example.asus.calculator.tools.adapter.SuggestionsProductAdapter;
import com.example.asus.calculator.tools.handler.SwitchButtonHandler;
import com.example.asus.calculator.tools.loader.LazyLoader;
import com.example.asus.calculator.tools.loader.ProductLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;
import com.example.asus.calculator.tools.loader.SuggestionProductLoader;
import com.example.asus.calculator.util.MagicConstants;
import com.example.asus.calculator.util.PreferenceUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.CALORIFIC_PREFERENCE;
import static com.example.asus.calculator.util.MagicConstants.CATEGORY_INTENT_EXTRA;
import static com.example.asus.calculator.util.MagicConstants.DISH_ACTIVITY_INTENT_EXTRA;
import static com.example.asus.calculator.util.MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS;

public class SearchActivity extends ListActivity implements SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<Cursor>, DrawerLayout.DrawerListener {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    private ProductAdapter adapter;
    private SuggestionsProductAdapter suggestionsAdapter;
    private FloatingActionButton floatButton;
    private SearchView searchView;

    private String mCurFilter;
    private String querySubmit;
    private Category category;

    private ResponseListener<ProductModel> listener = new ResponseListener<ProductModel>() {
        @Override
        public void onResponse(List<ProductModel> list) {
            adapter.clear();
            adapter.addAll(list);
        }
    };

    private ResponseListener<ProductModel> lazyListener = new ResponseListener<ProductModel>() {
        @Override
        public void onResponse(List<ProductModel> list) {
            adapter.addAll(list);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.i(LOG_TAG, "search Activity created");
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView drawer = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout.addDrawerListener(this);

        Log.i(LOG_TAG, "Preferences have been refreshed");
        PreferenceUtil.refresh(this, CALORIFIC_PREFERENCE);

        SwitchButtonHandler handler = new SwitchButtonHandler(this);
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_high).getActionView());
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_medium).getActionView());
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_low).getActionView());
        handler.listenCalorificSwitches();

        Intent intent = getIntent();
        final Intent oldIntent = new Intent(getApplicationContext(), DishActivity.class);
        if (intent.getSerializableExtra(MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS) != null) {
            String key = MagicConstants.OLD_CHECKED_PRODUCTS;
            oldIntent.putExtra(key, intent.getSerializableExtra(MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS));
        }
        List<ProductModel> list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getListView().setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    Log.i(LOG_TAG, "loadMore()");
                    ProductLoadTask task = new ProductLoadTask(adapter.getCount(), lazyListener, SearchActivity.this);
                    task.execute(querySubmit, Long.toString(category.getId()));
                }
            }
        });


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        suggestionsAdapter = new SuggestionsProductAdapter(this, null, true);
        searchView.setSuggestionsAdapter(suggestionsAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = suggestionsAdapter.getCursor();
                cursor.moveToPosition(position);
                String value = cursor.getString(cursor.getColumnIndex(Product.NAME));
                searchView.setQuery(value, true);
                return true;
            }
        });

        floatButton = (FloatingActionButton) findViewById(R.id.floating_button_product);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldIntent.putExtra(DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_product);
                oldIntent.putExtra(SEARCH_ACTIVITY_CHECKED_PRODUCTS, (Serializable) adapter.getCheckedList());
                startActivity(oldIntent);
            }
        });

        category = (Category) intent.getSerializableExtra(CATEGORY_INTENT_EXTRA);
        TextView textView = (TextView) drawer.getHeaderView(0).findViewById(R.id.tv_navigation_header_primary);
        textView.setText("Category: " + category.getName());
        Log.i(LOG_TAG, "received category -> id: " + category.getId() + ", name: " + category.getName());
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, true);
            Log.d(LOG_TAG, "query from search: " + query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, "onQueryTextSubmit: " + query);
        searchView.clearFocus();
        querySubmit = query;
        ProductLoadTask task = new ProductLoadTask(0, listener, this);
        task.execute(query, Long.toString(category.getId()));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "onQueryTextChange: " + newText);
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        if (mCurFilter == null && newFilter == null) {
            return true;
        }

        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }
        mCurFilter = newFilter;

        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SuggestionProductLoader loader = new SuggestionProductLoader(this);
        loader.setProductName(mCurFilter);
        loader.setCategory_id(Long.toString(category.getId()));
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        suggestionsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        suggestionsAdapter.swapCursor(null);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        //String builder = PreferenceUtil.addConditionsTest(this);
        Snackbar snackbar = Snackbar.make(floatButton, R.string.snackbar_text, Snackbar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
