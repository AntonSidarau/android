package com.example.googlemap.domain;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SimpleMarker implements Marker {
    private LatLng position;
    private MarkerOptions marker;
    private String title;

    public SimpleMarker(String title, LatLng position) {
        this(title, position.latitude, position.longitude);
    }

    public SimpleMarker(String title, double latitude, double longitude) {
        position = new LatLng(latitude, longitude);
        this.title = title;
        marker = new MarkerOptions().position(position).title(this.title);
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
