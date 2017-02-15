package com.example.googlemap.tool.location;

import io.reactivex.Observable;

public interface ILocationDataStore {
    Observable<LocationEntity> getLocation();
}
