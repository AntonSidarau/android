package com.example.asus.calculator.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.Model;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.CalculatorApplication;
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.adapter.delegate.ProductAdapterDelegate;
import com.example.asus.calculator.tools.loader.LazyLoaderRecycle;
import com.example.asus.calculator.tools.loader.ProductFullLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class RecycleActivity extends AppCompatActivity {
    private static final String TAG = RecycleActivity.class.getSimpleName();

    @Inject ProductModelRecycleAdapter adapter;
    private RecyclerView recyclerView;
    private ResponseListener<Model> lazyListener;
    private List<Model> list;
    private PublishSubject<Boolean> subject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        CalculatorApplication.get().getRecycleViewComponent().inject(this);
        subject = PublishSubject.create();

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        list = new ArrayList<>();
        adapter.setList(list);
        //adapter = new ProductModelRecycleAdapter(list);
        ProductAdapterDelegate delegate = new ProductAdapterDelegate();
        delegate.setSubject(subject);
        adapter.addDelegates(delegate);

        lazyListener = list1 -> {
            adapter.addAll(list1);
            adapter.notifyDataSetChanged();
        };

        ProductFullLoadTask task = new ProductFullLoadTask(adapter.getItemCount(), getApplicationContext(),
                lazyListener);
        task.execute();

        /* наркомания */
        subject
                .observeOn(Schedulers.computation())
                .doOnNext(aBoolean -> {
                    for (Model model : list) {
                        ((ProductModel) model).setChecked(aBoolean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> adapter.notifyDataSetChanged());


        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new LazyLoaderRecycle(manager) {
            @Override
            public void loadMore(RecyclerView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "loadMore() executed");
                ProductFullLoadTask task = new ProductFullLoadTask(adapter.getItemCount(), getApplicationContext(),
                        lazyListener);
                task.execute();
            }
        });
    }
}

