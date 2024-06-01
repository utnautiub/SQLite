package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.Cart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CartRepository {
    private DatabaseHelper dbHelper;

    public CartRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addCart(Cart cart) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", cart.getUserId());
        values.put("created_at", cart.getCreatedAt());
        db.insert("carts", null, values);
        db.close();
    }

    public Cart getCartByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("carts", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int userIdIndex = cursor.getColumnIndex("user_id");
            int createdAtIndex = cursor.getColumnIndex("created_at");

            if (idIndex != -1  && createdAtIndex != -1) {
                Cart cart = new Cart(cursor.getInt(idIndex),
                        cursor.getInt(userIdIndex),
                        cursor.getString(createdAtIndex));
                cursor.close();
                return cart;
            } else {
                cursor.close();
                throw new IllegalStateException("Database schema has changed. Missing columns.");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }



    public Cart getOrCreateCartByUserId(int userId) {
        Cart cart = getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(getCurrentTime());
            addCart(cart);
            cart = getCartByUserId(userId);
        }
        return cart;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
