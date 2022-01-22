package com.example.teste02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.LocationAndroid.NearbyRestaurants;
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

        List<Restaurante> l =NearbyRestaurants.showRestaurantesRadius(mapaRestaurantes,myLocation.latitude,myLocation.longitude);
        for (Restaurante r: l)
        {
            AddPinPoint(r);
        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,15f));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                setContentView(R.layout.activity_maps);
                break;
            case R.id.secondlayout:
                setContentView(R.layout.secondlayout);
            case R.id.thirdlayout:
                setContentView(R.layout.thirdlayout);
            case R.id.exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    RatingBar ratingStars;
    public void starBarMethod(){

        ratingStars = findViewById(R.id.ratingBar);
        ratingStars = findViewById(R.id.ratingBar);
        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating = (int) v;
                String msg = null;

                switch (rating){
                    case 1:
                        msg = ";(";
                        break;
                    case 2:
                        msg = ":(";
                        break;
                    case 3:
                        msg = ":|";
                        break;
                    case 4:
                        msg = ":)";
                        break;
                    case 5:
                        msg = ":D";
                        break;
                }
                Toast.makeText(MapsActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //metodo ligado ao button(android:onClick="procuraRestauranteNome")
    public void procuraRestauranteNome(View view) {
        EditText etNome = findViewById(R.id.textEditRestaurant);

        for(Restaurante r : mapaRestaurantes.values()){
            if(r.getNome().equals(etNome)){
                LatLng rest = new LatLng(r.getLatitude(),r.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(rest).title(r.getNome()));//mudar o icon / cor??
            }
        }
    }
}