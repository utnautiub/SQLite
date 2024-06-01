package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private DatabaseHelper dbHelper;

    public InvoiceRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addInvoice(Invoice invoice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", invoice.getUserId());
        values.put("total_price", invoice.getTotalPrice());
        values.put("created_at", invoice.getCreatedAt());
        db.insert("invoices", null, values);
        db.close();
    }

    public void updateInvoice(Invoice invoice) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", invoice.getUserId());
        values.put("total_price", invoice.getTotalPrice());
        values.put("created_at", invoice.getCreatedAt());
        db.update("invoices", values, "invoice_id = ?", new String[]{String.valueOf(invoice.getInvoiceId())});
        db.close();
    }

    public void deleteInvoice(int invoiceId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("invoices", "invoice_id = ?", new String[]{String.valueOf(invoiceId)});
        db.close();
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM invoices", null);

        int invoiceIdIndex = cursor.getColumnIndex("invoice_id");
        int userIdIndex = cursor.getColumnIndex("user_id");
        int totalPriceIndex = cursor.getColumnIndex("total_price");
        int createdAtIndex = cursor.getColumnIndex("created_at");

        if (cursor.moveToFirst()) {
            do {
                int invoiceId = cursor.getInt(invoiceIdIndex);
                int userId = cursor.getInt(userIdIndex);
                double totalPrice = cursor.getDouble(totalPriceIndex);
                String createdAt = cursor.getString(createdAtIndex);
                Invoice invoice = new Invoice(invoiceId, userId, totalPrice, createdAt);
                invoiceList.add(invoice);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return invoiceList;
    }
}