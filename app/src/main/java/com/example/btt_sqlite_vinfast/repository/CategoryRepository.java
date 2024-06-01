package com.example.btt_sqlite_vinfast.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btt_sqlite_vinfast.DatabaseHelper;
import com.example.btt_sqlite_vinfast.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private DatabaseHelper dbHelper;

    public CategoryRepository(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_name", category.getName());
        db.insert("categories", null, values);
        db.close();
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("category_name", category.getName());
        db.update("categories", values, "category_id = ?", new String[]{String.valueOf(category.getId())});
        db.close();
    }

    public void deleteCategory(int categoryId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("categories", "category_id = ?", new String[]{String.valueOf(categoryId)});
        db.close();
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);

        // Getting column indices safely
        int idIndex = cursor.getColumnIndex("category_id");
        int nameIndex = cursor.getColumnIndex("category_name");


        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                Category category = new Category(id, name);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoryList;
    }
}
