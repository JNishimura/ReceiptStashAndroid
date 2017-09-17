package com.HackUIowa17.receiptstash;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.database.Cursor;
import java.util.ArrayList;
import android.view.View;
import android.content.Intent;

import java.util.List;

public class ReceiptsActivity extends ListActivity {

    private ListView lv;

    //Parameters for SQLite query
    static final String[] PROJECTION = new String[] {DatabaseContract.DatabaseEntry.ORDERID_COLUMN,
            DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN,
            DatabaseContract.DatabaseEntry.PRICE_COLUMN,
            DatabaseContract.DatabaseEntry.QUANTITY_COLUMN,
            DatabaseContract.DatabaseEntry.DATE_COLUMN
    };

    String selection = DatabaseContract.DatabaseEntry.ORDERID_COLUMN + " > ?";
    String[] selectionArgs = {"0"};
    String sortOrder = DatabaseContract.DatabaseEntry.ORDERID_COLUMN + " DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        //Making query to get all transactions for user
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                DatabaseContract.DatabaseEntry.TABLE_NAME,
                PROJECTION,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if(cursor.moveToFirst()) {
            //Summing up each order's individual transactions into totals, then adding them to list to display
            int pastOrderId = cursor.getInt(0);
            String pastDate = cursor.getString(4);
            int totalPrice = 0;

            List txnStrings = new ArrayList<String>();
            do {
                System.out.println(cursor.getString(4));
                if (pastOrderId == cursor.getInt(0)) {
                    System.out.println("Product is: " + cursor.getInt(2) * cursor.getInt(3));
                    totalPrice += cursor.getInt(2) * cursor.getInt(3);
                } else {
                    txnStrings.add("Order #: " + pastOrderId + " Date: " + pastDate + " Total: $" + totalPrice);
                    totalPrice = cursor.getInt(2) * cursor.getInt(3);
                    pastOrderId = cursor.getInt(0);
                    pastDate = cursor.getString(4);
                }
            } while (cursor.moveToNext());

            txnStrings.add("Order #: " + pastOrderId + " Date: " + pastDate + " Total: $" + totalPrice);

            //Setting listView to display txnStrings
            lv = (ListView) findViewById(android.R.id.list);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    txnStrings
            );
            lv.setAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {
        //When listView item is clicked, we send the transaction total to IndividualReceipt, to be parsed for displaying individual order's receipt in detail
        super.onListItemClick(l, v, pos, id);
        Intent intent = new Intent(this, IndividualReceipt.class);
        intent.putExtra("message", (String) l.getItemAtPosition(pos));
        startActivity(intent);
    }
}
