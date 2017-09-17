package com.HackUIowa17.receiptstash;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class CameraActivity extends AppCompatActivity {

    //String containing where to print logs
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    //Necessary int to use onActivityResult method.  This gives the runtime environment a way to track which process needs which data returned
    private static final int BARCODE_READER_REQUEST_CODE = 1;

    private final String SYNC_URL = "";

    private TextView mResultTextView;

    //Some logic in this method modified from source: https://github.com/varvet/BarcodeReaderSample
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initializing activity to have displayed data
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //Linking textView in xml file to TextView class here
        mResultTextView = (TextView) findViewById(R.id.result_textview);

        //Linking button in xml file to Button class here
        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            //Opens view with camera when user presses button to scan QR code
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });
    }

    //Logic in this method modified from source: https://github.com/varvet/BarcodeReaderSample
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Once we get result from QR scanner, make sure process succeeded
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    //Pulling data from received Intent and displaying it in textView
                    //TODO: Change from displaying in textView to making a POST request
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    //Adding transaction based on QR Code data
                    addTransaction(barcode.displayValue);
                    mResultTextView.setText("Order Added");

                } else mResultTextView.setText(R.string.no_barcode_captured);
            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                    CommonStatusCodes.getStatusCodeString(resultCode)));
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private void addTransaction(String txnCode) {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        long newRowId = 0;

        switch(txnCode) {
            case "txn1":
                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Nintendo Switch");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "1");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 299.99);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 1);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "05/05/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);

                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Breath of the Wild");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "1");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 59.99);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 1);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "05/05/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);

                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Snipperclips");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "1");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 19.99);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 1);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "05/05/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
                break;
            case "txn2":
                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Cat Food");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "2");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 20);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 3);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "06/06/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);

                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Dog Food");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "2");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 15);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 2);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "06/06/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
                break;
            case "txn3":
                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "T-Shirt");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "3");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 15);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 2);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "07/27/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);

                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Jeans");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "3");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 40);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 1);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "07/27/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);

                values.put(DatabaseContract.DatabaseEntry.ITEMNAME_COLUMN, "Jordan 4s");
                values.put(DatabaseContract.DatabaseEntry.ORDERID_COLUMN, "3");
                values.put(DatabaseContract.DatabaseEntry.PRICE_COLUMN, 190);
                values.put(DatabaseContract.DatabaseEntry.QUANTITY_COLUMN, 1);
                values.put(DatabaseContract.DatabaseEntry.DATE_COLUMN, "07/27/2017");
                newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
                break;
        }
    }

}
