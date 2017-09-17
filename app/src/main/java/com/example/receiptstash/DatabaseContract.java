package com.example.receiptstash;

import android.provider.BaseColumns;

/**
 * Created by jacobnishimura on 9/16/17.
 */

public final class DatabaseContract {
    private DatabaseContract() { }

    public static class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String ORDERID_COLUMN = "orderID";
        public static final String ITEMNAME_COLUMN = "itemName";
        public static final String PRICE_COLUMN = "price";

        private static final String SQL_CREATE_TABLE = "CREATE TABLE" + DatabaseEntry.TABLE_NAME +
                " (" + DatabaseEntry.ORDERID_COLUMN + " ORDER ID," + DatabaseEntry.ITEMNAME_COLUMN +
                " ITEM NAME," + DatabaseEntry.PRICE_COLUMN + " PRICE)";
    }
}
