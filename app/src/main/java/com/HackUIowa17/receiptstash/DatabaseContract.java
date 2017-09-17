package com.HackUIowa17.receiptstash;

import android.provider.BaseColumns;

/**
 * Created by jacobnishimura on 9/16/17.
 * Class which stores essential data for interacting with SQLite database
 */

public final class DatabaseContract {
    //Private constructor so nobody can instantiate this class
    private DatabaseContract() { }

    //Class with strings for all column names
    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String ORDERID_COLUMN = "orderID";
        public static final String ITEMNAME_COLUMN = "itemName";
        public static final String PRICE_COLUMN = "price";
        public static final String QUANTITY_COLUMN = "quantity";
        public static final String DATE_COLUMN = "date";
    }
}
