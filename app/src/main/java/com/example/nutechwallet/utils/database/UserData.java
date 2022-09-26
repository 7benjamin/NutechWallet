package com.example.nutechwallet.utils.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.nutechwallet.utils.Config;
import java.util.ArrayList;
import java.util.List;


public class UserData {

    private static final String TABLE       ="USER";
    private static final String _ID         ="_ID";
    private static final String EMAIL       ="EMAIL";
    private static final String FIRST_NAME  ="FIRST_NAME";
    private static final String LAST_NAME   ="LAST_NAME";
    private static final String TOKEN       ="TOKEN";


    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +EMAIL+" TEXT, "
                    +FIRST_NAME+" TEXT, "
                    +LAST_NAME+" TEXT, "
                    +TOKEN+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;


    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public UserData(Context context) {
        this.context = context;
    }

    private UserData open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, Config.SQLITE_NAME, Config.SQLITE_VERSION);
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public UserMetaData save(UserMetaData metaData){

        if (count() == 1)
            deleteAll();

        try {

            open();
            sqLiteDatabase = databaseHelper.getWritableDatabase();

            ContentValues v = new ContentValues();
            v.put(EMAIL, metaData.getEmail());
            v.put(FIRST_NAME, metaData.getFirstName());
            v.put(LAST_NAME, metaData.getLastName());
            v.put(TOKEN, metaData.getToken());


            if (metaData.getSqliteId() == null) {
                Long id = sqLiteDatabase.insert(TABLE, null, v);
                metaData.setSqliteId(id.intValue());
            } else {
                sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(metaData.getSqliteId())});
            }

            close();
            return metaData;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    public void deleteAll() {
        String q = "DELETE FROM "+TABLE;
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL(q);
        close();
    }

    public List<UserMetaData> selectList() {
        try {

            String q = "SELECT * FROM " + TABLE + " WHERE 1=1 ";

            open();
            sqLiteDatabase = databaseHelper.getReadableDatabase();
            Cursor c = sqLiteDatabase.rawQuery(q, null);

            List<UserMetaData> list = new ArrayList<>();
            UserMetaData metaData;
            if (c.moveToFirst()) {
                do {
                    metaData = new UserMetaData();
                    metaData.setSqliteId(c.getInt(c.getColumnIndex(_ID)));
                    metaData.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                    metaData.setFirstName(c.getString(c.getColumnIndex(FIRST_NAME)));
                    metaData.setLastName(c.getString(c.getColumnIndex(LAST_NAME)));
                    metaData.setToken(c.getString(c.getColumnIndex(TOKEN)));

                    list.add(metaData);
                } while (c.moveToNext());
            }

            close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    public int count() {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }
}

