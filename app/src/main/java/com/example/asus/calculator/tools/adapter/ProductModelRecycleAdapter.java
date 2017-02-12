package com.example.asus.calculator.tools.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.asus.calculator.model.Model;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

public class ProductModelRecycleAdapter extends RecyclerView.Adapter {
    private static final String TAG = ProductModelRecycleAdapter.class.getSimpleName();

    private AdapterDelegatesManager<List<Model>> manager;
    private List<Model> list;

    public ProductModelRecycleAdapter(List<Model> list) {
        this.list = list;
        manager = new AdapterDelegatesManager<>();
    }

    public void addDelegates(AdapterDelegate<List<Model>> delegate) {
        manager.addDelegate(delegate);
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

    public void addAll(List<Model> newList) {
        list.addAll(newList);
    }
}
