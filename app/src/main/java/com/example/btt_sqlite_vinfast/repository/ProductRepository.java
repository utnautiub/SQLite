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
        values.put("product_id", product.getCode());
        values.put("product_name", product.getName());
        values.put("url_image", product.getImageUrl());
        values.put("category_id", product.getCategoryId());
        values.put("price", product.getPrice());
        db.insert("products", null, values);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);
        int codeIndex = cursor.getColumnIndex("product_id");
        int nameIndex = cursor.getColumnIndex("product_name");
        int imageUrlIndex = cursor.getColumnIndex("url_image");
        int priceIndex = cursor.getColumnIndex("price");
        int categoryIdIndex = cursor.getColumnIndex("category_id");

        if (cursor.moveToFirst()) {
            do {
                int code = cursor.getInt(codeIndex);
                String name = cursor.getString(nameIndex);
                String imageUrl = cursor.getString(imageUrlIndex);
                double price = cursor.getDouble(priceIndex);
                int categoryId = cursor.getInt(categoryIdIndex);

                Product product = new Product(code, name, imageUrl, price, categoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public Product getProductById(int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("products", null, "product_id=?", new String[]{String.valueOf(productId)}, null, null, null);

        int codeIndex = cursor.getColumnIndex("product_id");
        int nameIndex = cursor.getColumnIndex("product_name");
        int imageUrlIndex = cursor.getColumnIndex("url_image");
        int priceIndex = cursor.getColumnIndex("price");
        int categoryIdIndex = cursor.getColumnIndex("category_id");

        if (cursor != null && cursor.moveToFirst()) {
            int code = cursor.getInt(codeIndex);
            String name = cursor.getString(nameIndex);
            String imageUrl = cursor.getString(imageUrlIndex);
            double price = cursor.getDouble(priceIndex);
            int categoryId = cursor.getInt(categoryIdIndex);

            Product product = new Product(code, name, imageUrl, price, categoryId);
            cursor.close();
            db.close();
            return product;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }
}
