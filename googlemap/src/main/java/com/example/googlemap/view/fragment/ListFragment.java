package com.example.googlemap.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googlemap.R;
import com.example.googlemap.domain.SimpleMarker;
import com.example.googlemap.tool.adapter.MarkerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();


    @BindView(R.id.rv_markers) RecyclerView recyclerView;
    private List<SimpleMarker> simpleMarkers;
    private MarkerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        simpleMarkers = new ArrayList<>();
        simpleMarkers.add(new SimpleMarker("m1", 1, 1));
        simpleMarkers.add(new SimpleMarker("m2", 1, 1));
        simpleMarkers.add(new SimpleMarker("m3", 1, 1));
        simpleMarkers.add(new SimpleMarker("m4", 1, 1));
        simpleMarkers.add(new SimpleMarker("m5", 1, 1));
        simpleMarkers.add(new SimpleMarker("m6", 1, 1));

        adapter = new MarkerAdapter(simpleMarkers);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
}
