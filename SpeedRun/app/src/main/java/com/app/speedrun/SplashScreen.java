package com.app.speedrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.app.speedrun.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        DAO dao = new DAO(getApplicationContext(), "app", "access");

        if (!Boolean.parseBoolean(dao.get("normal"))) {
            changeAct(6000);
            dao.set("normal", "true");
        } else {
            changeAct(3000);
        }

    }

    private void changeAct(long millis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }
        }, millis);
    }
}