package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.InvoiceItem;

import java.util.ArrayList;
import java.util.List;

public class InvoiceItemRepository {
    private DatabaseHelper dbHelper;

    public InvoiceItemRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("invoice_id", invoiceItem.getInvoiceId());
        values.put("product_id", invoiceItem.getProductId());
        values.put("quantity", invoiceItem.getQuantity());
        values.put("price", invoiceItem.getPrice());
        db.insert("invoice_items", null, values);
        db.close();
    }

    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("invoice_id", invoiceItem.getInvoiceId());
        values.put("product_id", invoiceItem.getProductId());
        values.put("quantity", invoiceItem.getQuantity());
        values.put("price", invoiceItem.getPrice());
        db.update("invoice_items", values, "invoice_item_id = ?", new String[]{String.valueOf(invoiceItem.getInvoiceItemId())});
        db.close();
    }

    public void deleteInvoiceItem(int invoiceItemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("invoice_items", "invoice_item_id = ?", new String[]{String.valueOf(invoiceItemId)});
        db.close();
    }

    public List<InvoiceItem> getAllInvoiceItems() {
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM invoice_items", null);

        int invoiceItemIdIndex = cursor.getColumnIndex("invoice_item_id");
        int invoiceIdIndex = cursor.getColumnIndex("invoice_id");
        int productIdIndex = cursor.getColumnIndex("product_id");
        int quantityIndex = cursor.getColumnIndex("quantity");
        int priceIndex = cursor.getColumnIndex("price");

        if (cursor.moveToFirst()) {
            do {
                int invoiceItemId = cursor.getInt(invoiceItemIdIndex);
                int invoiceId = cursor.getInt(invoiceIdIndex);
                int productId = cursor.getInt(productIdIndex);
                int quantity = cursor.getInt(quantityIndex);
                double price = cursor.getDouble(priceIndex);
                InvoiceItem invoiceItem = new InvoiceItem(invoiceItemId, invoiceId, productId, quantity, price);
                invoiceItemList.add(invoiceItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return invoiceItemList;
    }
}