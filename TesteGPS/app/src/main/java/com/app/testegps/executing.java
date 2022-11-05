package com.app.testegps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.app.testegps.databinding.ActivityExecutingBinding;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
public class executing extends AppCompatActivity {

    ActivityExecutingBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityExecutingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();
    }

    private void init() {
        content();
        b.initBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cronometro.pause();
                startActivity(new Intent(executing.this, Paused.class));
            }
        });
    }

    private void content() {
        b.time.setText(Cronometro.getTimer());

        refresh(1);
    }


    private void refresh(long millis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                content();
            }
        }, millis);
    }
}