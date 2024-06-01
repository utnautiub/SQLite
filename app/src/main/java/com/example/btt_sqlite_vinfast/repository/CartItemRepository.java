package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartItemRepository {
    private DatabaseHelper dbHelper;

    public CartItemRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addOrUpdateCartItem(CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("cart_items", null, "cart_id=? AND product_id=?",
                new String[]{String.valueOf(cartItem.getCartId()), String.valueOf(cartItem.getProductId())},
                null, null, null);

        int quantityIndex = cursor.getColumnIndex("quantity");

        if (cursor != null && cursor.moveToFirst()) {
            int quantity = cursor.getInt(quantityIndex) + cartItem.getQuantity();
            ContentValues values = new ContentValues();
            values.put("quantity", quantity);
            db.update("cart_items", values, "cart_id=? AND product_id=?",
                    new String[]{String.valueOf(cartItem.getCartId()), String.valueOf(cartItem.getProductId())});
        } else {
            ContentValues values = new ContentValues();
            values.put("cart_id", cartItem.getCartId());
            values.put("product_id", cartItem.getProductId());
            values.put("quantity", cartItem.getQuantity());
            db.insert("cart_items", null, values);
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    public void updateCartItem(CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", cartItem.getQuantity());
        db.update("cart_items", values, "cart_item_id=?", new String[]{String.valueOf(cartItem.getCartItemId())});
        db.close();
    }

    public void deleteCartItem(int cartItemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart_items", "cart_item_id=?", new String[]{String.valueOf(cartItemId)});
        db.close();
    }

    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItemList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity " +
                "FROM cart_items ci " +
                "INNER JOIN carts c ON ci.cart_id = c.cart_id " +
                "WHERE c.user_id = ?", new String[]{String.valueOf(userId)});

        int cartItemIdIndex = cursor.getColumnIndex("cart_item_id");
        int cartIdIndex = cursor.getColumnIndex("cart_id");
        int productIdIndex = cursor.getColumnIndex("product_id");
        int quantityIndex = cursor.getColumnIndex("quantity");

        if (cursor.moveToFirst()) {
            do {
                int cartItemId = cursor.getInt(cartItemIdIndex);
                int cartId = cursor.getInt(cartIdIndex);
                int productId = cursor.getInt(productIdIndex);
                int quantity = cursor.getInt(quantityIndex);

                CartItem cartItem = new CartItem(cartItemId, cartId, productId, quantity);
                cartItemList.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItemList;
    }
}
