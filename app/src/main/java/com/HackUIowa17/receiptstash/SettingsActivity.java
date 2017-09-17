package com.example.receiptstash;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initializing db connection
        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
    }

    public void deleteAllReceipts(View view) {
        //Extra dialog to make sure user is certain they wish to remove all receipts
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Deletes SQLite database and creates new table in its place.
                dbHelper.onUpgrade(db, 1, 1);
                dialog.dismiss();;
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
           public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
