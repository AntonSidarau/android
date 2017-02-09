package com.example.asus.calculator.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.loader.LazyLoaderRecycle;
import com.example.asus.calculator.tools.loader.ProductFullLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;

import java.util.ArrayList;
import java.util.List;


public class RecycleActivity extends AppCompatActivity {
    private static final String LOG_TAG = RecycleActivity.class.getSimpleName();

    private ProductModelRecycleAdapter adapter;
    private RecyclerView recyclerView;
    private ResponseListener<ProductModel> lazyListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        List<ProductModel> list = new ArrayList<>();
        //adapter = new ProductRecycleAdapter(list, this);
        adapter = new ProductModelRecycleAdapter(this, list);

        lazyListener = new ResponseListener<ProductModel>() {
            @Override
            public void onResponse(List<ProductModel> list) {
                adapter.addAll(list);
                adapter.notifyDataSetChanged();
            }
        };

        ProductFullLoadTask task = new ProductFullLoadTask(adapter.getItemCount(), getApplicationContext(),
                lazyListener);
        task.execute();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new LazyLoaderRecycle(manager) {
            @Override
            public void loadMore(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(LOG_TAG, "loadMore() executed");
                ProductFullLoadTask task = new ProductFullLoadTask(adapter.getItemCount(), getApplicationContext(),
                        lazyListener);
                task.execute();
            }
        });


    }
}
