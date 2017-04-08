package com.omralcorut.orderfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    DatabaseHandler db;
    FoodHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //DB object
        db = new DatabaseHandler(this);

        final List<History> historyArrayList = db.getAllHistory();

        //Create list view with custom adapter
        final ListView historyListView = (ListView) findViewById(R.id.list);
        mAdapter = new FoodHistoryAdapter(this, historyArrayList);
        historyListView.setAdapter(mAdapter);
    }

    //pressed back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
