package com.example.teste02.LocationAndroid;

import com.example.teste02.Restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NearbyRestaurants {
    public static float maxRange = 500f;
    public static List<Restaurante> showRestaurantesRadius(Map<String,Restaurante> restaurantes, double mylat, double mylong){
        List<Restaurante> allRestaurantesNearby = new ArrayList<>();
        for(Restaurante r : restaurantes.values()){

            if(distance(r.getLatitude(),r.getLongitude(),mylat,mylong)< maxRange) {
                allRestaurantesNearby.add(r);
            }

        }
        return allRestaurantesNearby;
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1000;//metros
        return dist;
    }
    private static double deg2rad(double d)
    {
         return d * 3.1415925/180;
    }

    private static double rad2deg(double d)
    {
        return d * 180/3.1415925 ;
    }
}
