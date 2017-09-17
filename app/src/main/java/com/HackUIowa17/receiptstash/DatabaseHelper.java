package com.HackUIowa17.receiptstash;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by jacobnishimura on 9/16/17.
 * Class which includes helpful SQL queries and methods for interacting with the db more easily
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //Storing db version and name of db, in case we need to make changes to schema later, we won't use old db versions
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "orderDB.db";

    //Query to intially create table
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DatabaseContract.DatabaseEntry.TABLE_NAME +
            " (" + DatabaseContract.DatabaseEntry.ORDERID_COLUMN + " ORDER_ID," + DatabaseContract.DatabaseEntry.DATE_COLUMN + " DATE," +
            DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN + " ITEM_NAME," + DatabaseContract.DatabaseEntry.QUANTITY_COLUMN + " QUANTITY, " +
            DatabaseContract.DatabaseEntry.PRICE_COLUMN + " PRICE)";

    //Query to delete table
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseContract.DatabaseEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates empty database when called
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //Deletes database and creates new database, used when schema changes, or when we need to wipe database and create new
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
