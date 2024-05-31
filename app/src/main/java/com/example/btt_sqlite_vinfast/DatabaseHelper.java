package com.example.btt_sqlite_vinfast;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VinfastSales.db";

    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CARTS = "carts";
    private static final String TABLE_CART_ITEMS = "cart_items";
    private static final String TABLE_INVOICES = "invoices";
    private static final String TABLE_INVOICE_ITEMS = "invoice_items";

    // Users Table - column names
    private static final String COL_USER_ID = "user_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_FULL_NAME = "full_name";
    private static final String COL_ROLE = "role";

    // Categories Table - column names
    private static final String COL_CATEGORY_ID = "category_id";
    private static final String COL_CATEGORY_NAME = "category_name";

    // Products Table - column names
    private static final String COL_PRODUCT_ID = "product_id";
    private static final String COL_PRODUCT_NAME = "product_name";
    private static final String COL_URL_IMAGE = "url_image";
    private static final String COL_PRICE = "price";
    private static final String COL_PRODUCT_CATEGORY_ID = "category_id";

    // Carts Table - column names
    private static final String COL_CART_ID = "cart_id";
    private static final String COL_CART_USER_ID = "user_id";
    private static final String COL_CREATED_AT = "created_at";

    // Cart Items Table - column names
    private static final String COL_CART_ITEM_ID = "cart_item_id";
    private static final String COL_CART_ITEM_CART_ID = "cart_id";
    private static final String COL_CART_ITEM_PRODUCT_ID = "product_id";
    private static final String COL_QUANTITY = "quantity";

    // Invoices Table - column names
    private static final String COL_INVOICE_ID = "invoice_id";
    private static final String COL_INVOICE_USER_ID = "user_id";
    private static final String COL_TOTAL_PRICE = "total_price";
    private static final String COL_INVOICE_CREATED_AT = "created_at";

    // Invoice Items Table - column names
    private static final String COL_INVOICE_ITEM_ID = "invoice_item_id";
    private static final String COL_INVOICE_ITEM_INVOICE_ID = "invoice_id";
    private static final String COL_INVOICE_ITEM_PRODUCT_ID = "product_id";
    private static final String COL_INVOICE_ITEM_QUANTITY = "quantity";
    private static final String COL_INVOICE_ITEM_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_ID + " INTEGER PRIMARY KEY," + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT," + COL_FULL_NAME + " TEXT," + COL_ROLE + " TEXT" + ")";
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + COL_CATEGORY_ID + " INTEGER PRIMARY KEY," + COL_CATEGORY_NAME + " TEXT" + ")";
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COL_PRODUCT_ID + " INTEGER PRIMARY KEY," + COL_PRODUCT_NAME + " TEXT," + COL_URL_IMAGE + " TEXT,"
                + COL_PRICE + " REAL," + COL_PRODUCT_CATEGORY_ID + " INTEGER,"
                + "FOREIGN KEY(" + COL_PRODUCT_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COL_CATEGORY_ID + "))";
        String CREATE_CARTS_TABLE = "CREATE TABLE " + TABLE_CARTS + "("
                + COL_CART_ID + " INTEGER PRIMARY KEY," + COL_CART_USER_ID + " INTEGER,"
                + COL_CREATED_AT + " TEXT,"
                + "FOREIGN KEY(" + COL_CART_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "))";
        String CREATE_CART_ITEMS_TABLE = "CREATE TABLE " + TABLE_CART_ITEMS + "("
                + COL_CART_ITEM_ID + " INTEGER PRIMARY KEY," + COL_CART_ITEM_CART_ID + " INTEGER,"
                + COL_CART_ITEM_PRODUCT_ID + " INTEGER," + COL_QUANTITY + " INTEGER,"
                + "FOREIGN KEY(" + COL_CART_ITEM_CART_ID + ") REFERENCES " + TABLE_CARTS + "(" + COL_CART_ID + "),"
                + "FOREIGN KEY(" + COL_CART_ITEM_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COL_PRODUCT_ID + "))";
        String CREATE_INVOICES_TABLE = "CREATE TABLE " + TABLE_INVOICES + "("
                + COL_INVOICE_ID + " INTEGER PRIMARY KEY," + COL_INVOICE_USER_ID + " INTEGER,"
                + COL_TOTAL_PRICE + " REAL," + COL_INVOICE_CREATED_AT + " TEXT,"
                + "FOREIGN KEY(" + COL_INVOICE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "))";
        String CREATE_INVOICE_ITEMS_TABLE = "CREATE TABLE " + TABLE_INVOICE_ITEMS + "("
                + COL_INVOICE_ITEM_ID + " INTEGER PRIMARY KEY," + COL_INVOICE_ITEM_INVOICE_ID + " INTEGER,"
                + COL_INVOICE_ITEM_PRODUCT_ID + " INTEGER," + COL_INVOICE_ITEM_QUANTITY + " INTEGER,"
                + COL_INVOICE_ITEM_PRICE + " REAL,"
                + "FOREIGN KEY(" + COL_INVOICE_ITEM_INVOICE_ID + ") REFERENCES " + TABLE_INVOICES + "(" + COL_INVOICE_ID + "),"
                + "FOREIGN KEY(" + COL_INVOICE_ITEM_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COL_PRODUCT_ID + "))";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CARTS_TABLE);
        db.execSQL(CREATE_CART_ITEMS_TABLE);
        db.execSQL(CREATE_INVOICES_TABLE);
        db.execSQL(CREATE_INVOICE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE_ITEMS);
        onCreate(db);
    }
}
