package com.dreamgallery.mymoneybook.feature;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dreamgallery.mymoneybook.helper.MyDatabaseHelper;

public class MyDB {

    private SQLiteDatabase database;
    public final static String FINANCE_TABLE = "MyFinance";
    public final static String FINANCE_TYPE  = "type";
    public final static String FINANCE_TIME  = "time";
    public final static String FINANCE_fee   = "fee";
    public final static String FINANCE_note  = "note";
    public final static String FINANCE_budge = "budge";

    /**
     *
     * @param context Application context
     */
    public MyDB(Context context){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     *
     * @param type  Finance type
     * @param time  Finance time (yy/mm/dd)
     * @param fee   Finance fee (CNY)
     * @param note  Remarks
     * @param budge Budge
     */
    public void createRecords(String type, String time, String fee, String note, String budge){
        String sql = "insert into " + MyDB.FINANCE_TABLE + " (" + MyDB.FINANCE_TYPE + ", " +
                MyDB.FINANCE_TIME + ", " + MyDB.FINANCE_fee + ", " + MyDB.FINANCE_note + ", " +
                MyDB.FINANCE_budge + ") VALUES ('" + type + "', '" + time + "', '" + fee + "', '" + note +
                "', '" + budge + "')";
        database.execSQL(sql);
    }

    /**
     *
     * @return Cursor which has been edited
     */
    public Cursor selectRecords() {
        String[] cols = new String[] { FINANCE_TYPE, FINANCE_TIME, FINANCE_fee, FINANCE_note, FINANCE_budge };
        Cursor mCursor = database.query(true, FINANCE_TABLE, cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public void close() {
        database.close();
    }
}