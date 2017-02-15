package com.example.googlemap.tool;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import io.reactivex.subjects.PublishSubject;

public class MarkerScroller extends RecyclerView.OnScrollListener {
    private static final String TAG = MarkerScroller.class.getSimpleName();

    private int firstVisibleItem;
    private int prevFirstVisibleItem;
    private LinearLayoutManager manager;
    private PublishSubject<Integer> subject;

    public MarkerScroller(LinearLayoutManager manager) {
        this.manager = manager;
        firstVisibleItem = manager.findFirstVisibleItemPosition();
        prevFirstVisibleItem = firstVisibleItem;
    }

    public void setMarkerSubject(PublishSubject<Integer> subject) {
        this.subject = subject;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        firstVisibleItem = manager.findFirstVisibleItemPosition();
        if (firstVisibleItem != prevFirstVisibleItem) {
            Log.d(TAG, "onScrolled: need to find marker, pos: " + prevFirstVisibleItem);
            prevFirstVisibleItem = firstVisibleItem;
            subject.onNext(prevFirstVisibleItem - 1);
        }
    }
}
