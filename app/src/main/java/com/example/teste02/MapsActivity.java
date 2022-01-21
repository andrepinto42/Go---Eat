package com.example.teste02;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.teste02.databinding.ActivityMapsBinding;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ActivityMapsBinding binding;
    Map<String,Restaurante> mapaRestaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        mapaRestaurantes = Parser.ParseRestaurante(this);
        int i =0;
        for (Restaurante r :
                mapaRestaurantes.values()) {
            AddPinPoint(r);

            i++;
            if (i == 3) break;
        }
    }



    private void AddPinPoint(Restaurante restaurante) {
        LatLng teste = new LatLng(restaurante.getLatitude(), restaurante.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(teste).title(restaurante.getNome()));
        System.out.println("SET MARKER -> " + restaurante.getNome());
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(teste,1f));
    }

    public void ButonClick(View view) {
        if (view.getId() == R.id.zoomIn)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view.getId() == R.id.zoomOut)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
}