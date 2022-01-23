package com.example.teste02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.teste02.Fragments.FragmentAdapter;
import com.example.teste02.LocationAndroid.GetLocationUser;
import com.example.teste02.NearbySearch.DownloadUrl;
import com.example.teste02.NearbySearch.GetNearbyPlacesData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity  {
    private static final String TAG = "MapsActivity";
    public static GoogleMap googleMap;
    Map<String, Restaurante> mapaRestaurantes;
    FusedLocationProviderClient fusedLocationProviderClient;

    public static MapsActivity Singleton;

    public static double userLat= 41.549214f;
    public static double userLon= -8.424451f;
    EditText editTextSearch;

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    HashMap<Integer,String> mapKeywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        //Inicializar um singleton
        Singleton = this;

        mapKeywords = new HashMap<Integer,String>();
        mapKeywords.put(R.id.radioButtonMarisqueira,"sea food");
        mapKeywords.put(R.id.radioButtonChurrascaria,"barbecue");
        mapKeywords.put(R.id.radioButtonMexicano,"mexican");
        mapKeywords.put(R.id.radioButtonPizza,"pizza");
        mapKeywords.put(R.id.radioButtonVegetariano,"vegan");
        mapKeywords.put(R.id.radioButtonSushi,"sushi");

        InitializeTabLayout();
        //Texto de procura
        //editTextSearch = findViewById(R.id.textEditRestaurant);

        //Getting fusedLocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);

        GetLocationUser.StartGettingLocation(this,fusedLocationProviderClient);
    }

    private void InitializeTabLayout() {
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm,getLifecycle());
        pager2.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Mapa"));
        tabLayout.addTab(tabLayout.newTab().setText("Filtros"));
        tabLayout.addTab(tabLayout.newTab().setText("Outro"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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

    public void ButonClick(View view) {
        if (view.getId() == R.id.zoom_in)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view.getId() == R.id.zoom_out)
        {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }


    public void NearbySearch(View view)
    {

        if (allKeywords.size() == 1)
            allKeywords.remove("restaurant");

        if (allKeywords.size() == 0)
            allKeywords.add("restaurant");

        Log.d("onClick", "Button is Clicked");
        googleMap.clear();
        for (String s: allKeywords) {
            String keyword = s;
            String url = DownloadUrl.getUrl(userLat, userLon, keyword,500);
            Object[] DataTransfer = new Object[3];
            DataTransfer[0] = googleMap;
            DataTransfer[1] = url;
            DataTransfer[2] = 20 /   ((allKeywords.size() > 0) ? allKeywords.size() : 1);
            Log.d("onClick", url);
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
            getNearbyPlacesData.execute(DataTransfer);
        }
        pager2.setCurrentItem(0);

        Toast.makeText(MapsActivity.this,"Nearby Restaurants", Toast.LENGTH_LONG).show();
    }



    String filterOption = "";

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

    private List<String> allKeywords = new ArrayList<String>();
    public void ApplyFilter(View v)
    {
        String currentKeyword = mapKeywords.get( v.getId());
        CheckBox checkBox = findViewById(v.getId());
        if (allKeywords.contains(currentKeyword ) )
            allKeywords.remove( currentKeyword);
        else
            allKeywords.add(currentKeyword);

    }

    public void CloseKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view == null) return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void SetBackgroundAvaliar(boolean enabled)
    {
        ConstraintLayout constraintLayout= (ConstraintLayout) findViewById(R.id.layoutAvaliarRestaurante);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button avaliarButton = (Button) findViewById(R.id.avaliarButton);
        ratingBar.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        avaliarButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        constraintLayout.setBackgroundColor((enabled) ? Color.rgb(255,236,215) : Color.TRANSPARENT);

    }



}