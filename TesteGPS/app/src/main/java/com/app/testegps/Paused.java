package com.app.testegps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.app.testegps.databinding.ActivityExecutingBinding;
import com.app.testegps.databinding.ActivityPausedBinding;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Paused extends AppCompatActivity {

    ActivityPausedBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityPausedBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        init();
    }

    private void init() {
        content();

        b.res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Cronometro.resume();
            }
        });

        b.conc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Paused.this, Concluir.class));
                finish();
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