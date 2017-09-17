package com.HackUIowa17.receiptstash;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class IndividualReceipt extends AppCompatActivity {
    //View in attached activity
    private ListView lv;

    //Parameters for SQLite query
    static final String[] PROJECTION = new String[] {DatabaseContract.DatabaseEntry.ORDERID_COLUMN,
            DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN,
            DatabaseContract.DatabaseEntry.PRICE_COLUMN,
            DatabaseContract.DatabaseEntry.QUANTITY_COLUMN,
            DatabaseContract.DatabaseEntry.DATE_COLUMN
    };
    String selection = DatabaseContract.DatabaseEntry.ORDERID_COLUMN + " = ?";
    String[] selectionArgs = {"0"};
    String sortOrder = DatabaseContract.DatabaseEntry.ORDERID_COLUMN + " DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize display with nothing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_receipt);

        //Get receipt we passed from ReceiptsActivity
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");

        //Parse to get orderID
        String brokenMessage[] = message.split(" ");
        selectionArgs[0] = brokenMessage[2];

        //Write and execute query to get all transactions with given orderID
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

        List txnStrings = new ArrayList<String>();

        cursor.moveToFirst();

        //Add each transaction as a string to txnStrings and add to total price
        int total = 0;
        do {
            txnStrings.add("Price: $" + cursor.getInt(2) + " x " + "Quantity: " + cursor.getInt(3)+ "   Item Name: " + cursor.getString(1));
            total += cursor.getInt(2) * cursor.getInt(3);
        } while (cursor.moveToNext());

        txnStrings.add("Transaction Total: $" + total);

        //Assigning data in txnStrings to our listView
        lv = (ListView) findViewById(android.R.id.list);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                txnStrings
        );
        lv.setAdapter(arrayAdapter);
    }
}
