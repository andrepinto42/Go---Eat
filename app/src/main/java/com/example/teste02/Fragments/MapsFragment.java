package com.example.teste02.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.MapsActivity;
import com.example.teste02.NearbySearch.DataParser;
import com.example.teste02.R;
import com.example.teste02.Restaurante;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsActivity.googleMap = googleMap;

            googleMap.setOnMarkerClickListener(marker -> {

                String markerName = marker.getTitle();
                if (DataParser.mapNearbyRestaurant == null || DataParser.mapNearbyRestaurant.get(markerName) == null) return false;
                Restaurante r = DataParser.mapNearbyRestaurant.get(markerName);
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


}