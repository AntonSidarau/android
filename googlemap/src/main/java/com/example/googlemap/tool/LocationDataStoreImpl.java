package com.example.googlemap.tool;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.googlemap.tool.location.ILocationCache;
import com.example.googlemap.tool.location.ILocationDataStore;
import com.example.googlemap.tool.location.LocationEntity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class LocationDataStoreImpl implements ILocationDataStore, LocationListener {

    private static final int GPS_UPDATE_TIME_FREQUENCY = 800;
    private static final int GPS_UPDATE_DISTANCE_FREQUENCY = 1;
    private static final int MAX_ADDRESS_COUNT = 1;

    private PublishSubject<LocationEntity> locationSubject;
    private final Context context;
    private final ILocationCache cache;
    private final Geocoder geocoder;

    public LocationDataStoreImpl(final Context context, final ILocationCache cache) {
        this.context = context;
        this.cache = cache;
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public Observable<LocationEntity> getLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location;
        if ((location = locationManager.getLastKnownLocation(GPS_PROVIDER)) == null && (location = locationManager.getLastKnownLocation(NETWORK_PROVIDER)) == null) {
            locationSubject = PublishSubject.create();
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, GPS_UPDATE_TIME_FREQUENCY, GPS_UPDATE_DISTANCE_FREQUENCY, this);
            locationManager.requestLocationUpdates(GPS_PROVIDER, GPS_UPDATE_TIME_FREQUENCY, GPS_UPDATE_DISTANCE_FREQUENCY, this);
            return locationSubject;
        } else {
            return Observable.just(getLocation(location));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            returnLocation(location);
        }
    }

    private void returnLocation(@NonNull Location location) {
        LocationEntity locationEntity = getLocation(location);
        locationSubject.onNext(locationEntity);
        locationSubject.onComplete();
    }

    @NonNull
    private LocationEntity getLocation(@NonNull final Location location) {
        LocationEntity entity = new LocationEntity();
        entity.setLatitude(location.getLatitude());
        entity.setLongitude(location.getLongitude());
        entity.setTitle(getAddressTitle(location));
        cache.put(entity);
        return entity;
    }

    private String getAddressTitle(@NonNull final Location location) {
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), MAX_ADDRESS_COUNT);
            Address address;
            if (!addressList.isEmpty() && (address = addressList.get(0)) != null) {
                return getAddressTitle(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getAddressTitle(@NonNull final Address address) {
        String addressTitle;
        if ((addressTitle = address.getThoroughfare()) != null) {
            return addressTitle;
        }
        if ((addressTitle = address.getFeatureName()) != null) {
            return addressTitle;
        }
        if ((addressTitle = address.getSubAdminArea()) != null) {
            return addressTitle;
        }
        if ((addressTitle = address.getLocality()) != null) {
            return addressTitle;
        }
        if ((addressTitle = address.getSubLocality()) != null) {
            return addressTitle;
        }
        if ((addressTitle = address.getCountryName()) != null) {
            return addressTitle;
        }
        return "";
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
