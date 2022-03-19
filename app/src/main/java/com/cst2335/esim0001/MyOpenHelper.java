package com.cst2335.esim0001;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "ChatRoomActivity";
    public static final String filename = "MyDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "MyData";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE = "Message";
    public static final String COL_SEND_RECEIVE = "isSent";

    public MyOpenHelper(Context context) {
        super(context, filename, null, version);
    }

    //Table creation.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MESSAGE + " TEXT, " +
                COL_SEND_RECEIVE + " INTEGER);";
        db.execSQL(query);
    }

    //if table already exists, it is dropped and a new table is created.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        this.onCreate(db); //calls function on line 26
    }

}
