package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private DatabaseHelper dbHelper;

    public ProductRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_code", product.getCode());
        values.put("product_name", product.getName());
        values.put("url_image", product.getImageUrl());
        values.put("price", product.getPrice());
        values.put("category_id", product.getCategoryId());
        db.insert("products", null, values);
        db.close();
    }
}
