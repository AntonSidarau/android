package com.example.asus.calculator.tools.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.adapter.delegate.ProductAdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

public class ProductModelRecycleAdapter extends RecyclerView.Adapter {
    private static final String TAG = ProductModelRecycleAdapter.class.getSimpleName();

    private AdapterDelegatesManager<List<ProductModel>> manager;
    private List<ProductModel> list;

    public ProductModelRecycleAdapter(Activity activity, List<ProductModel> list) {
        this.list = list;
        manager = new AdapterDelegatesManager<>();
        manager.addDelegate(new ProductAdapterDelegate(activity));
    }

    @Override
    public int getItemViewType(int position) {
        return manager.getItemViewType(list, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return manager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        manager.onBindViewHolder(list, position, holder);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ProductModel> newList) {
        list.addAll(newList);
    }

    public List<ProductModel> getList() {
        return list;
    }
}
