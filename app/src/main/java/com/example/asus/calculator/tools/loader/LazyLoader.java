package com.example.asus.calculator.tools.loader;

import android.widget.AbsListView;


public abstract class LazyLoader implements AbsListView.OnScrollListener {
    private boolean isLoading;
    private int previousTotal;

    private int currentScrollState;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // if adapter has been cleared
        if (totalItemCount < previousTotal) {
            previousTotal = 0;
        }

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!isLoading && (visibleItemCount == (totalItemCount - firstVisibleItem)
                /*&& this.currentScrollState == SCROLL_STATE_IDLE*/)) {
            isLoading = true;
            loadMore(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public abstract void loadMore(AbsListView view, int firstVisibleItem,
                                  int visibleItemCount, int totalItemCount);
}
