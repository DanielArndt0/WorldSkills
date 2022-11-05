package com.app.speedrun;

import android.content.Context;
import android.content.SharedPreferences;

public class DB {

    private final Context context;
    private final String filename;

    public DB(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public SharedPreferences.Editor set() {
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
    }

    public SharedPreferences get() {
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }
}
