package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.Cart;

import java.util.ArrayList;
import java.util.List;

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

    public void updateCart(Cart cart) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", cart.getUserId());
        values.put("created_at", cart.getCreatedAt());
        db.update("carts", values, "cart_id = ?", new String[]{String.valueOf(cart.getId())});
        db.close();
    }

    public void deleteCart(int cartId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("carts", "cart_id = ?", new String[]{String.valueOf(cartId)});
        db.close();
    }

    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM carts", null);

        int CartIdIndex = cursor.getColumnIndex("cart_id");
        int UserIdIndex = cursor.getColumnIndex("user_id");
        int CreatedAtIndex = cursor.getColumnIndex("created_at");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(CartIdIndex);
                int userId = cursor.getInt(UserIdIndex);
                String createdAt = cursor.getString(CreatedAtIndex);
                Cart cart = new Cart(id, userId, createdAt);
                carts.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return carts;
    }

    public Cart getCartByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cart cart = null;
        Cursor cursor = db.query("carts", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("cart_id");
            int createdAtIndex = cursor.getColumnIndex("created_at");

            if (idIndex != -1 && createdAtIndex != -1) {
                int id = cursor.getInt(idIndex);
                String createdAt = cursor.getString(createdAtIndex);
                cart = new Cart(id, userId, createdAt);
            }
            cursor.close();
        }
        db.close();
        return cart;
    }

}