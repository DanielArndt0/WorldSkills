package com.app.testegps;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Cronometro {
    private Cronometro() {}
    static private Long timer = 0L;
    static private Timer counter;

    static public void resume() {
        try {
            counter = new Timer();
            counter.scheduleAtFixedRate(new TimerTask() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    timer++;
                }
            }, 0, 1);
        } catch (Exception ignore) {}
    }

    static public void pause() {
        try {
            counter.cancel();
        } catch (Exception ignore) {}
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    static public String getTimer() {
        return DateTimeFormatter.ofPattern("HH:mm:s:SS")
                .withZone(ZoneId.of("UTC"))
                .format(Instant.ofEpochMilli(timer));
    }

    static public void reset() { timer = 0L; }
}
