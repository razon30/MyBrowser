package com.example.razon30.mybrowser;

/**
 * Created by razon30 on 04-05-15.
 */
import java.util.ArrayList;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DtabaseHelper extends SQLiteOpenHelper {
    Context context;

    public static final String DATABASE_NAME = "Browser_Managment";
    public static final int DB_VERSION = 1;

    public static final String DB_TABLE_NAME = "Browser_data";
    public static final String ID_FIELD = "_id";
    public static final String LINK_NAME = "link_name";
    public static final String LINK = "link";
    public static final String SIGN = "sign";

    public static final String BROWSER_TABLE = "CREATE TABLE " + DB_TABLE_NAME
            + " ( " + ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LINK_NAME + " TEXT, " + LINK + " TEXT, " + SIGN + " TEXT)";

    public DtabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);

        this.context = context;

        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(BROWSER_TABLE);
        Log.e("TABLE CREAT", BROWSER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public long insertdata(browserdata data) {
        // TODO Auto-generated method stub

        if (data.getLink().length() == 0 || data.getLink() == null || data.getLink().compareTo("")==0) {

            return -1;

        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LINK_NAME, data.getName());
        // values.put(PASSWORD, data.getPassword());
        values.put(LINK, data.getLink());
        long insert = db.insert(DB_TABLE_NAME, null, values);
        db.close();
        return insert;

    }

    public ArrayList<browserdata> searchresult() {
        // TODO Auto-generated method stub

        ArrayList<browserdata> dataArrayList = new ArrayList<browserdata>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.query(DB_TABLE_NAME, null, ID_FIELD + " LIKE '%"
        //		+ keyword + "%'", null, null, null, null);


        //Cursor cursor = db.query(DB_TABLE_NAME, null, null, null, null, null, null);

        String selectQuery = "SELECT  * FROM " + DB_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String name = cursor.getString(cursor
                        .getColumnIndex(LINK_NAME));
                String link = cursor.getString(cursor
                        .getColumnIndex(LINK));

                browserdata data = new browserdata(name, link);

                dataArrayList.add(data);
                cursor.moveToNext();

            }

        }

        cursor.close();
        db.close();
        return dataArrayList;
    }

    public int delete(String address) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = this.getReadableDatabase();

        //int dlt = db.delete(DB_TABLE_NAME, EXAMN_NAME + "=?", new String[] { ""
        //		+ dltid });

        int dlt = db.delete(DB_TABLE_NAME, null, null);

        db.close();
        return dlt;

    }

}

