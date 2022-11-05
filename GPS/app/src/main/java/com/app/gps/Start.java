package com.app.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.gps.databinding.ActivityStartBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Start extends AppCompatActivity implements OnMapReadyCallback {

    ActivityStartBinding b;
    ActionBar actionBar;
    LocationManager locationManager;
    LocationListener locationListener;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();
    }

    @SuppressLint({"DefaultLocale"})
    private void init() {
        DAO dao = new DAO(getApplicationContext(), "app", "global");
        setSupportActionBar(b.toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(dao.get("sport"));

        // Location
        locationManager = (LocationManager) getSystemService(MainActivity.LOCATION_SERVICE);
        locationListener = new Location();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Start.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Provider habilitado!", Toast.LENGTH_SHORT).show();
        }

        Log.d("deb", String.format("Lat: %.2f   Lon: %.2f", Location.getLat(), Location.getLon()));

        // Fragmento GPS
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng loc = new LatLng(Location.getLat(), Location.getLon());
        map.addMarker(new MarkerOptions().position(loc).title("Você está aqui"));
        map.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}