package com.example.googlemap.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googlemap.R;
import com.example.googlemap.domain.Marker;
import com.example.googlemap.domain.SimpleMarker;
import com.example.googlemap.tool.MarkerScroller;
import com.example.googlemap.tool.adapter.SimpleMarkerAdapter;
import com.example.googlemap.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class MarkerListFragment extends Fragment implements MainActivity.OnMarkerAddListener {
    private static final String TAG = MarkerListFragment.class.getSimpleName();

    @BindView(R.id.rv_markers) RecyclerView recyclerView;
    private List<Marker> simpleMarkers;
    private SimpleMarkerAdapter adapter;
    private LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        Log.d(TAG, "onCreateView: entered");
        simpleMarkers = new ArrayList<>();
        simpleMarkers.add(new SimpleMarker("m1", 53.396434, -105.820312));
        simpleMarkers.add(new SimpleMarker("m2", 57.385785, 13.007813));
        simpleMarkers.add(new SimpleMarker("m3", 48.994638, 12.304688));
        simpleMarkers.add(new SimpleMarker("m4", 31.447413, 5.273438));
        simpleMarkers.add(new SimpleMarker("m5", 57.385785, 43.945313));
        simpleMarkers.add(new SimpleMarker("m6", 5.725314, -66.09375));
        simpleMarkers.add(new SimpleMarker("m7", -4.105367, 16.523438));
        simpleMarkers.add(new SimpleMarker("m8", 27.46929, 45.351563));
        simpleMarkers.add(new SimpleMarker("m9", 56.035227, -78.75));
        simpleMarkers.add(new SimpleMarker("m10", 65.41259, 82.617188));
        simpleMarkers.add(new SimpleMarker("m11", 16.74143, 46.054688));
        simpleMarkers.add(new SimpleMarker("m12", 39.453163, 115.3125));
        simpleMarkers.add(new SimpleMarker("m13", -9.34067, -20.039062));
        simpleMarkers.add(new SimpleMarker("m14", -4.105367, 109.335938));
        simpleMarkers.add(new SimpleMarker("m15", 46.149396, 27.773438));
        simpleMarkers.add(new SimpleMarker("m16", 58.135922, 10.898438));
        simpleMarkers.add(new SimpleMarker("m17", 60.2943, 24.960938));

        adapter = new SimpleMarkerAdapter(simpleMarkers);
        manager = new LinearLayoutManager(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);
        return rootView;
    }

    public void setScrollingSubject(PublishSubject<Integer> subject) {
        MarkerScroller scroller = new MarkerScroller(manager);
        scroller.setMarkerSubject(subject);
        recyclerView.addOnScrollListener(scroller);
    }

    public List<Marker> getMarkerList() {
        return simpleMarkers;
    }

    @Override
    public void add(Marker marker) {
        simpleMarkers.add(marker);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: entered");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: entered");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: entered");
    }

}
