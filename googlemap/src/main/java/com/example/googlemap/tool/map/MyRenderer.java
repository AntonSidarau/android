package com.example.googlemap.tool.map;

import android.content.Context;

import com.example.googlemap.domain.Marker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MyRenderer extends DefaultClusterRenderer<Marker> {
    public MyRenderer(Context context, GoogleMap map, ClusterManager<Marker> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Marker item, MarkerOptions markerOptions) {
        markerOptions.title(item.getMarker().getTitle())
                .snippet(item.getMarker().getTitle());
    }
}
