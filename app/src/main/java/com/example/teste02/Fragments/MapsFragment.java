package com.example.teste02.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.MapsActivity;
import com.example.teste02.NearbySearch.DataParser;
import com.example.teste02.R;
import com.example.teste02.SistemData.Restaurante;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsFragment extends Fragment  {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsActivity.googleMap = googleMap;

            GetLocationUser.StartGettingLocation(MapsActivity.Singleton,MapsActivity.Singleton.fusedLocationProviderClient);

            googleMap.setOnMarkerClickListener(marker -> {

                String markerName = marker.getTitle();
                if (DataParser.mapNearbyRestaurant == null || DataParser.mapNearbyRestaurant.get(markerName) == null) return false;
                Restaurante r = DataParser.mapNearbyRestaurant.get(markerName);

                MapsActivity.Singleton.currentMarker = marker;
                MapsActivity.Singleton.SetBackgroundAvaliar(true);

                return false;
            });

            //Quando o usuario clica no mapa é desabilitado o butao de avaliar
            googleMap.setOnMapClickListener(maker -> {
                System.out.println("Actived map clicker");
                MapsActivity.Singleton.SetBackgroundAvaliar(false);
            });


        }
    };

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


    public String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;

    }

}