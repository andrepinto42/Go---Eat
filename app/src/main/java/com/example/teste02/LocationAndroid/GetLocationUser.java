package com.example.teste02.LocationAndroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.core.app.ActivityCompat;
import com.example.teste02.MapsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocationUser {

    public static Task<Location> findLocation;

    public static void StartGettingLocation(MapsActivity mapsActivity, FusedLocationProviderClient fusedLocationProviderClient) {
        //Check permissions
        if (ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mapsActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //When permission is denied
                ActivityCompat.requestPermissions(mapsActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        } else {

            System.out.println("Trying to get Last Location");
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            findLocation = task;

            task.addOnSuccessListener(location -> {
                System.out.println("Sucess on finding Location");
                System.out.println(location + " ÇLocation");
                if (location != null) {

                    double currentLat = location.getLatitude();
                    double currentLong = location.getLongitude();
                    MapsActivity.userLat = currentLat;
                    MapsActivity.userLon = currentLong;

                    System.out.println(currentLong + " Latitude ---- Longitude  " +currentLat);
                }
            });


        }
    }
}
