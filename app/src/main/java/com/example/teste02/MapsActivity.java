package com.example.teste02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.teste02.Fragments.FragmentAdapter;
import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.LocationAndroid.NearbyRestaurants;
import com.example.teste02.NearbySearch.DownloadUrl;
import com.example.teste02.NearbySearch.GetNearbyPlacesData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap googleMap;
    Map<String, Restaurante> mapaRestaurantes;
    FusedLocationProviderClient fusedLocationProviderClient;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //Getting fusedLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
    }

    //Metodo por enquanto nao vai ser usado
    private void TabInitializer() {

        //tabLayout = findViewById(R.id.tab_layout);
        //pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("First"));
        tabLayout.addTab(tabLayout.newTab().setText("Second"));
        tabLayout.addTab(tabLayout.newTab().setText("Third"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAB SELECTED", "onTabSelected: Here");
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        //mapaRestaurantes = Parser.ParseRestaurante(this);

        //Getting Current Location
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Permission has been denied
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
        }*/

        //Inutil por agora buscar a localizacao atraves deste metodo
        GetLocationUser.StartGettingLocation(this,fusedLocationProviderClient);
    }

    private void AddPinPoint(Restaurante restaurante) {
        LatLng teste = new LatLng(restaurante.getLatitude(), restaurante.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(teste).title(restaurante.getNome()));
        System.out.println("SET MARKER -> " + restaurante.getNome());
    }

    public void ButonClick(View view) {
        if(GetLocationUser.userAddress != null)
        {
            Log.d("Location", "ButonClick: "+GetLocationUser.userAddress.getLatitude());
        }
        if (view.getId() == R.id.zoomIn)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view.getId() == R.id.zoomOut)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);

        menuInflater.inflate(R.menu.preferencias, menu);
        MenuItem item_pizza = menu.findItem(R.id.manuPizza);
        MenuItem item_Marisco = menu.findItem(R.id.manuMarisco);
        MenuItem item_Churrasco = menu.findItem(R.id.menuChurrasco);
        if(filterOption.equals("pizza")){
            item_pizza.setChecked(true);

        }else if(filterOption.equals("marisco")){
            item_Marisco.setChecked(true);

        }else if(filterOption.equals("churrasco")){
            item_Churrasco.setChecked(true);
        }

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
                break;
            case R.id.thirdlayout:
                setContentView(R.layout.thirdlayout);
                break;
            case R.id.exit:
                finish();
                break;
            default:
                return false;
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("LocationChanged", "onLocationChanged: "+location.getLatitude() +" ----- " +location.getLongitude());
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

    public void sellectFilter(View view){
        registerForContextMenu(view);
        openContextMenu(view);

    }

    String filterOption = null;

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.manuPizza:
                Toast.makeText(getApplicationContext(),"Pizzeria sellected",Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                filterOption = "pizza";
                return true;
            case R.id.manuMarisco:
                Toast.makeText(getApplicationContext(),"Marisqueira sellected",Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                filterOption = "marisco";
                return true;
            case R.id.menuChurrasco:
                Toast.makeText(getApplicationContext(),"Churrasqueria sellected",Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                filterOption = "churrasc";
                return true;

                
        }
        return super.onContextItemSelected(item);
    }
}