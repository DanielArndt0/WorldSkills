package com.app.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.gps.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        listeners();
    }

    private void listeners() {
        setSupportActionBar(b.toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        b.runBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = new DAO(getApplicationContext(), "app", "global");
                dao.set("sport", "Corrida");
                startActivity(new Intent(MainActivity.this, Start.class));
                finish();
            }
        });

        b.camBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO dao = new DAO(getApplicationContext(), "app", "global");
                dao.set("sport", "Caminhada");
                startActivity(new Intent(MainActivity.this, Start.class));
                finish();
            }
        });
    }
}