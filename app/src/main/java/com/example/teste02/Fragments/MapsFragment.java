package com.example.teste02.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teste02.MapsActivity;
import com.example.teste02.NearbySearch.DataParser;
import com.example.teste02.R;
import com.example.teste02.SistemData.Restaurante;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsFragment extends Fragment {

    private final String TAG = "SEARCHLOCATION";
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsActivity.googleMap = googleMap;


            googleMap.setOnMarkerClickListener(marker -> {

                String markerName = marker.getTitle();
                if (DataParser.mapNearbyRestaurant == null || DataParser.mapNearbyRestaurant.get(markerName) == null)
                    return false;
                Restaurante r = DataParser.mapNearbyRestaurant.get(markerName);

                MapsActivity.Singleton.currentMarker = marker;
                MapsActivity.Singleton.SetBackgroundAvaliar(true);

                return false;
            });

            //Quando o usuario clica no mapa é desabilitado o butao de avaliar
            googleMap.setOnMapClickListener(maker -> {
                System.out.println("Actived map clicker");
                MapsActivity.Singleton.setTextDistanciaAteLugar("Distancia :");
                MapsActivity.Singleton.setTextTempoAteLugar("Tempo :");
                MapsActivity.Singleton.SetBackgroundAvaliar(false);
            });

            GetLocationUser();
        }
    };

    private void GetLocationUser() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.Singleton, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsActivity.Singleton, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.Singleton, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return;
        }
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        MapsActivity.Singleton.userLat = location.getLatitude();
                        MapsActivity.Singleton.userLon = location.getLongitude();
                        Log.d(TAG, "onLocationResult: " + location.getLatitude() + "   " + location.getLongitude());
                    }
                }
            }
        };


        LocationServices.getFusedLocationProviderClient(MapsActivity.Singleton).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }




}