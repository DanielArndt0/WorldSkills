package com.app.speedrun;

import android.content.Context;

public class DAO {
    private final DB db;
    private final String dbquerry;
    private final String id;

    public DAO(Context context, String filename, String id) {
        this.db = new DB(context, filename);
        this.dbquerry = context.getString(R.string.querry);
        this.id = id;
    }

    public void set(String key, String value) {
        db.set().putString(String.format(dbquerry, id, key), value).apply();
    }

    public String get(String key) {
        return db.get().getString(String.format(dbquerry, id, key), "");
    }
}
