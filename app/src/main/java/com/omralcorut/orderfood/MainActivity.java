package com.omralcorut.orderfood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    TextView table;
    public static int tableNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //custom actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setElevation(0);

        //DB object
        db = new DatabaseHandler(this);

        table = (TextView) findViewById(R.id.welcome_table);

        //set table number to textview
        Intent intent = getIntent();
        tableNumber = intent.getIntExtra(IntroActivity.QRCODE, 0);
        table.setText("Welcome to Table " + tableNumber);
    }

    //Open menu activity
    public void menu(View v) {
        Intent toMenu = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(toMenu);
    }

    //Open ask for check activity
    public void askForCheck(View v) {
        Intent toBill = new Intent(MainActivity.this, AskForCheckActivity.class);
        startActivity(toBill);
    }

    //Send calling waiter request
    public void callWaiter(View v) {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Call Waiter");
        adb.setMessage("Are you sure to want to call waiter?");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                db.addHistory(new History("The waiter was called.",tableNumber+""));
                Toast.makeText(MainActivity.this,"The waiter is called.",Toast.LENGTH_LONG).show();
            }});
        adb.show();
    }

    //open my ordered activity
    public void myOrdered(View v) {
        Intent toHistory = new Intent(MainActivity.this, MyOrdersActivity.class);
        startActivity(toHistory);
    }
}
