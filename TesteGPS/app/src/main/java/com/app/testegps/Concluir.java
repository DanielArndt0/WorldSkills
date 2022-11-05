package com.app.testegps;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.app.testegps.databinding.ActivityConcluirBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Concluir extends AppCompatActivity implements OnMapReadyCallback {

    ActivityConcluirBinding b;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityConcluirBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();
    }


    private void init(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmaps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        b.time.setText(Cronometro.getTimer());

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(Location.getLocation(this)).title("Você está aqui!"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Location.getLocation(this)));
        map.setMinZoomPreference(10.f);
    }
}