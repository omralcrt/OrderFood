package com.omralcorut.orderfood;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    FoodAdapter mAdapter;
    DatabaseHandler db;
    private FloatingActionButton fab;
    ArrayList<Food> foodArrayList;
    TextView cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("nameOfMenu"));

        //DB object
        db = new DatabaseHandler(this);

        cartCount = (TextView) findViewById(R.id.cart_count);
        cartCount.setText(db.getCartsCount()+"");

        foodArrayList = new ArrayList<Food>();

        //pull food data from txt file
        try {
            foodArrayList = PlayWithRawFiles(getIntent().getIntExtra("currentMenu", 0));
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }


        //Create list view with custom adapter
        final ListView foodListView = (ListView) findViewById(R.id.list);
        mAdapter = new FoodAdapter(this, foodArrayList,0);
        foodListView.setAdapter(mAdapter);

        //When click list item, add food to my orders
        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.addCart(foodArrayList.get(i));
                cartCount.setText(db.getCartsCount()+"");
            }
        });

        //when click fab button go to cart activity
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    //Update cart count
    @Override
    protected void onResume() {
        super.onResume();
        cartCount.setText(db.getCartsCount()+"");
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

    //read food items from storage
    public ArrayList<Food> PlayWithRawFiles(int id) throws IOException {
        ArrayList<Food> result = new ArrayList<Food>();
        String str = "";
        int fileResourceId = id;
        InputStream is = this.getResources().openRawResource(fileResourceId);
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
        if (is != null) {
            while ((str = reader.readLine()) != null) {
                String[] parts = str.split(",");
                result.add(new Food(parts[0], parts[1] + " TL"));
            }
        }
        reader.close();
        is.close();
        return result;
    }
}
