package com.app.gps;

import android.location.LocationListener;

import androidx.annotation.NonNull;

import java.util.List;

public class Location implements LocationListener {

    static private double lat = 0;
    static private double lon = 0;

    public Location() {}

    static public double getLat() {
        return lat;
    }

    static public double getLon() {
        return lon;
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onLocationChanged(@NonNull List<android.location.Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
