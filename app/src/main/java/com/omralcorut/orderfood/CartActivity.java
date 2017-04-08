package com.omralcorut.orderfood;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    FoodAdapter mAdapter;
    DatabaseHandler db;
    TextView totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //DB object
        db = new DatabaseHandler(this);

        totalCost = (TextView) findViewById(R.id.total_cost);
        totalCost.setText(db.cartCost()+" TL");

        final List<Food> carts = db.getAllCart();

        //Create list view with custom adapter
        final ListView cartListView = (ListView) findViewById(R.id.list);
        mAdapter = new FoodAdapter(this, carts,1);
        cartListView.setAdapter(mAdapter);

        //When click list item, delete food from my cart
        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                db.deleteCart(carts.get(i));
                mAdapter.remove(carts.get(i));
                totalCost.setText(db.cartCost()+" TL");
            }
        });
    }

    //Update list view
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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

    //clear all cart
    public void emptyCart(View v) {
        AlertDialog.Builder adb = new AlertDialog.Builder(CartActivity.this);
        adb.setTitle("Delete All?");
        adb.setMessage("Are you sure to want to delete all cart items?");
        adb.setNegativeButton("Cancel", null);
        adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                db.deleteCarts();
                mAdapter.clear();
                totalCost.setText(db.cartCost()+" TL");
            }});
        adb.show();
    }

    //when ordered your cart, send all cart to bill and clear cart
    public void orderCart(View v) {
        if (db.getAllCart().isEmpty()) {
            Toast.makeText(CartActivity.this,"The cart is empty!", Toast.LENGTH_LONG).show();
        }
        else {
            AlertDialog.Builder adb = new AlertDialog.Builder(CartActivity.this);
            adb.setTitle("Order Okay?");
            adb.setMessage("If finished your order, please click yes.");
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    List<Food> cart = db.getAllCart();
                    for (int i=0; i<cart.size(); i++) {
                        db.addBill(cart.get(i));
                    }
                    db.addHistory(new History("Ordered food for "+db.cartCost()+" TL",MainActivity.tableNumber+""));
                    db.deleteCarts();
                    mAdapter.clear();
                    totalCost.setText(db.cartCost()+" TL");
                    Toast.makeText(CartActivity.this,"The order is sent, it will be prepared soon.",Toast.LENGTH_LONG).show();
                }});
            adb.show();
        }
    }
}
