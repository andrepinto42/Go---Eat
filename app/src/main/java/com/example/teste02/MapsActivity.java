package com.example.teste02;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.LocationAndroid.NearbyRestaurants;
import com.example.teste02.NearbySearch.DownloadUrl;
import com.example.teste02.NearbySearch.GetNearbyPlacesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.teste02.databinding.ActivityMapsBinding;

import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ActivityMapsBinding binding;
    Map<String, Restaurante> mapaRestaurantes;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Getting fusedLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        mapaRestaurantes = Parser.ParseRestaurante(this);

        GetLocationUser.StartGettingLocation(this,fusedLocationProviderClient);

        int i =0;

    }

    private void AddPinPoint(Restaurante restaurante) {
        LatLng teste = new LatLng(restaurante.getLatitude(), restaurante.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(teste).title(restaurante.getNome()));
        System.out.println("SET MARKER -> " + restaurante.getNome());
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

    public void SearchArea(View view) {
        LatLng myLocation = new LatLng(41.553016, -8.427365);
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("You are here!"));

        NearbyRestaurants.maxRange = 500f;
        List<Restaurante> l =NearbyRestaurants.showRestaurantesRadius(mapaRestaurantes,myLocation.latitude,myLocation.longitude);
        for (Restaurante r: l)
        {
            AddPinPoint(r);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15f));
    }


    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,0).show();
            }
            return false;
        }
        return true;
    }

    public void Search(View view)
    {
        Log.d("onClick", "Button is Clicked");
        googleMap.clear();
        String url = DownloadUrl.getUrl(41.553016, -8.427365, "restaurant",500);
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = googleMap;
        DataTransfer[1] = url;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
        Toast.makeText(MapsActivity.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();
    }
}