package com.dreamgallery.mymoneybook.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author James
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DreamDB08";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table MyFinance " +
    "( _id integer primary key AUTOINCREMENT, type varchar(62) not null, time type varchar(62) not null, " +
            "fee type varchar(62) not null, note type varchar(62) not null, budge varchar(62) not null)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("Constructor...");

    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        System.out.println("Creating...");
        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS MyFinance");
        onCreate(database);
    }

    @Override
    public synchronized void close() {
        super.close();
        System.out.println("Closing ... database ...");
    }
}