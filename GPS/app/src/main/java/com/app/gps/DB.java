package com.app.gps;

import android.content.Context;
import android.content.SharedPreferences;

public class DB {
    Context context;
    String string;

    public DB(Context context, String string) {
        this.context = context;
        this.string = string;
    }

    public SharedPreferences.Editor set() {
        return context.getSharedPreferences(string, Context.MODE_PRIVATE).edit();
    }

    public SharedPreferences get() {
        return context.getSharedPreferences(string, Context.MODE_PRIVATE);
    }
}
