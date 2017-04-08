package com.omralcorut.orderfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by omral on 24.12.2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ordersManager";

    // Contacts table names
    private static final String TABLE_CART = "cart";
    private static final String TABLE_BILL = "bill";
    private static final String TABLE_HISTORY = "history";

    // Contacts Table Columns names
    private static final String KEY_ID_C = "id_c";
    private static final String KEY_ID_B = "id_b";
    private static final String KEY_ID_H = "id_h";
    private static final String KEY_FOOD_NAME_C = "food_name_c";
    private static final String KEY_FOOD_COST_C = "food_cost_c";
    private static final String KEY_FOOD_NAME_B = "food_name_b";
    private static final String KEY_FOOD_COST_B = "food_cost_b";
    private static final String KEY_EVENT = "food_event";
    private static final String KEY_TABLE_NUMBER = "table_number";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_ID_C + " INTEGER PRIMARY KEY," + KEY_FOOD_NAME_C + " TEXT,"
                + KEY_FOOD_COST_C + " TEXT" + ")";
        String CREATE_BILL_TABLE = "CREATE TABLE " + TABLE_BILL + "("
                + KEY_ID_B + " INTEGER PRIMARY KEY," + KEY_FOOD_NAME_B + " TEXT,"
                + KEY_FOOD_COST_B + " TEXT" + ")";
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID_H + " INTEGER PRIMARY KEY," + KEY_EVENT + " TEXT,"
                + KEY_TABLE_NUMBER + " TEXT," + KEY_DATE + " TEXT"  + ")";
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_BILL_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // Create tables again
        onCreate(db);
    }

    // Adding new cart
    void addCart(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME_C, food.getName());
        values.put(KEY_FOOD_COST_C, food.getCost());

        // Inserting Row
        db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection
    }

    // Getting All carts
    public List<Food> getAllCart() {
        List<Food> cartList = new ArrayList<Food>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setCost(cursor.getString(2));
                // Adding cart to list
                cartList.add(food);
            } while (cursor.moveToNext());
        }

        // return cart list
        return cartList;
    }

    // Deleting single cart
    public void deleteCart(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_ID_C + " = ?",
                new String[] { String.valueOf(food.getId()) });
        db.close();
    }

    // Deleting all cart
    public void deleteCarts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    // Getting carts Count
    public int getCartsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }

    //get total cart cost
    public double cartCost() {
        double total = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to total
        if (cursor.moveToFirst()) {
            do {
                String[] a = cursor.getString(2).split(" ");
                total = total + Double.parseDouble(a[0]);
            } while (cursor.moveToNext());
        }

        // return total cart cost
        return total;
    }

    // Adding new bill
    void addBill(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_NAME_B, food.getName());
        values.put(KEY_FOOD_COST_B, food.getCost());

        // Inserting Row
        db.insert(TABLE_BILL, null, values);
        db.close(); // Closing database connection
    }

    // Getting All bills
    public List<Food> getAllBill() {
        List<Food> billList = new ArrayList<Food>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BILL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setCost(cursor.getString(2));
                // Adding bill to list
                billList.add(food);
            } while (cursor.moveToNext());
        }

        // return bill list
        return billList;
    }

    // Deleting all bill
    public void deleteBills() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BILL, null, null);
        db.close();
    }

    //get total bill cost
    public double billCost() {
        double total = 0;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BILL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to total
        if (cursor.moveToFirst()) {
            do {
                String[] a = cursor.getString(2).split(" ");
                total = total + Double.parseDouble(a[0]);
            } while (cursor.moveToNext());
        }

        // return total cart cost
        return total;
    }

    // Adding new history
    void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar c = Calendar.getInstance();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT, history.getEvent());
        values.put(KEY_TABLE_NUMBER, history.getTableNumber());
        values.put(KEY_DATE, String.valueOf(c.getTimeInMillis()));

        // Inserting Row
        db.insert(TABLE_HISTORY, null, values);
        db.close(); // Closing database connection
    }

    // Getting All history
    public List<History> getAllHistory() {
        List<History> historyList = new ArrayList<History>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setEvent(cursor.getString(1));
                history.setTableNumber(cursor.getString(2));
                history.setDate(cursor.getString(3));
                // Adding history to list
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        // return history list
        return historyList;
    }

    // Deleting all history
    public void deleteHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, null, null);
        db.close();
    }
}
