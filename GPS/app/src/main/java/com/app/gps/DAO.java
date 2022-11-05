package com.app.gps;

import android.content.Context;

public class DAO {
    DB db;
    String sQuerry;
    String id;

    DAO(Context context, String filename, String id) {
        db = new DB(context, filename);
        sQuerry = "%s.%s";
        this.id = id;
    }

     public void set(String key, String value) {
        db.set().putString(String.format(sQuerry, id, key), value).apply();
     }

     public String get(String key) {
        return db.get().getString(String.format(sQuerry, id, key), "");
     }
}
