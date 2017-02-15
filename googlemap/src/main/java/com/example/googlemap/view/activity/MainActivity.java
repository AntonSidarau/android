package com.example.googlemap.view.activity;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlemap.R;
import com.example.googlemap.domain.Marker;
import com.example.googlemap.domain.SimpleMarker;
import com.example.googlemap.tool.map.MyRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ZOOM_LEVEL = 13;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;

    //@BindView(R.id.dl_activity_main) DrawerLayout drawerLayout;
    @BindView(R.id.btn_location) Button btnLocation;
    private GoogleMap googleMap;
    private ClusterManager<Marker> clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: entered");
        FragmentManager manager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) manager.findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: entered");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.setOnMapClickListener(this);
        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(false);

        if (checkLocationPermission()) {
            btnLocation.setVisibility(View.GONE);
            enableLocationFeatures();
        } else {
            btnLocation.setVisibility(View.VISIBLE);
        }

        clusterManager = new ClusterManager<>(getApplicationContext(), this.googleMap);
        clusterManager.addItem(new SimpleMarker("m1", 55.1709501, 30.214999));
        clusterManager.addItem(new SimpleMarker("m2", 55.1719500, 30.214999));
        clusterManager.addItem(new SimpleMarker("m3", 55.1729503, 30.214999));
        clusterManager.addItem(new SimpleMarker("m4", 55.1709501, 30.204998));
        clusterManager.addItem(new SimpleMarker("m5", 55.1709501, 30.225000));
        this.googleMap.setOnCameraIdleListener(clusterManager);
        clusterManager.setRenderer(new MyRenderer(this, this.googleMap, clusterManager));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        clusterManager.addItem(new SimpleMarker("Hey", latLng));
        clusterManager.cluster();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //temporary
        Location myLocation = this.googleMap.getMyLocation();
        if (myLocation == null) {
            Toast.makeText(getApplicationContext(), "Location is determining...", Toast.LENGTH_SHORT).show();
            return true;
        }

        LatLng myPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, ZOOM_LEVEL));
        Log.d(TAG, "onMyLocationButtonClick: position: " + myPosition);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableLocationFeatures();
            }
        }
    }

    @OnClick(R.id.btn_location)
    public void OnLocationClick() {
        if (!checkLocationPermission()) {
            requestLocationPermission();
        }
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_LOCATION_PERMISSION_REQUEST_CODE);
    }

    @SuppressWarnings("all")
    private void enableLocationFeatures() {
        btnLocation.setVisibility(View.GONE);
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setOnMyLocationButtonClickListener(this);
    }
}
