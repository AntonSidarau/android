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
import com.example.asus.calculator.tools.adapter.ProductModelRecycleAdapter;
import com.example.asus.calculator.tools.adapter.delegate.ProductAdapterDelegate;
import com.example.asus.calculator.tools.loader.LazyLoaderRecycle;
import com.example.asus.calculator.tools.loader.ProductFullLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RecycleActivity extends AppCompatActivity implements ProductAdapterDelegate.OnLongClickCheckBoxListener {
    private static final String TAG = RecycleActivity.class.getSimpleName();

    private ProductModelRecycleAdapter adapter;
    private RecyclerView recyclerView;
    private ResponseListener<Model> lazyListener;
    private List<Model> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        list = new ArrayList<>();
        adapter = new ProductModelRecycleAdapter(list);
        adapter.addDelegates(new ProductAdapterDelegate(this));

        lazyListener = list1 -> {
            adapter.addAll(list1);
            adapter.notifyDataSetChanged();
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
                Log.d(TAG, "loadMore() executed");
                ProductFullLoadTask task = new ProductFullLoadTask(adapter.getItemCount(), getApplicationContext(),
                        lazyListener);
                task.execute();
            }
        });
    }

    @Override
    public void update(final boolean newState) {
        //такое себе, постоянно пересоздается цепочка
        Observable.fromIterable(list)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "update: doOnComplete is on Thread: " + Thread.currentThread().getName());
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(model -> {
                    ((ProductModel) model).setChecked(newState);
                    Log.d(TAG, "update: subscribe is on Thread: " + Thread.currentThread().getName());
                });

        //тоже самое, но без лишних действий
        /*Observable.<ProductModel>create(e -> {
            for (Model model : list) {
                ((ProductModel)model).setChecked(newState);
            }
            e.onComplete();
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> adapter.notifyDataSetChanged())
                .subscribe();*/
    }
}

