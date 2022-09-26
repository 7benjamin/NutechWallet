package com.example.nutechwallet.utils.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public DatabaseHelper(Context context, String dbName, Integer dbVer) {
        super(context, dbName, null, dbVer);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserData.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL(UserData.DROP_TABLE);
        db.execSQL(UserData.CREATE_TABLE);

    }
}
