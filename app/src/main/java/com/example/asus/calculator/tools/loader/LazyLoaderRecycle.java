package com.example.asus.calculator.tools.loader;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public abstract class LazyLoaderRecycle extends RecyclerView.OnScrollListener {
    private LinearLayoutManager manager;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private int previousTotal;
    private boolean isLoading;

    public LazyLoaderRecycle(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = manager.getChildCount();
        totalItemCount = manager.getItemCount();
        firstVisibleItem = manager.findFirstVisibleItemPosition();

        if (totalItemCount < previousTotal) {
            previousTotal = 0;
        }

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!isLoading && (visibleItemCount == (totalItemCount - firstVisibleItem))) {
            isLoading = true;
            loadMore(recyclerView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public abstract void loadMore(RecyclerView view, int firstVisibleItem,
                                  int visibleItemCount, int totalItemCount);
}
