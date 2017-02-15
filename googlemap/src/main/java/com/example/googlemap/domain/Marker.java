package com.example.googlemap.domain;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

public interface Marker extends ClusterItem {
    MarkerOptions getMarker();
}
