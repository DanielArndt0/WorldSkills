package com.app.speedrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.speedrun.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityMainBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;
        DAO dao = new DAO(getApplicationContext(), "app", "access");
        DAO user = new DAO(getApplicationContext(), "app", dao.get("logged"));

        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.headerName)).setText(user.get("name"));
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.headerEmail)).setText(user.get("email"));


        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        if (item.getItemId() == R.id.about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Lorem Ipsum")
                    .setPositiveButton("Ok", null)
                    .setTitle("Sobre")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}