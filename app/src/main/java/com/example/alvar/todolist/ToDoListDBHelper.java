package com.example.alvar.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.alvar.todolist.ToDoListDatabaseContract.*;

/**
 * Created by Alvar on 03/02/2018.
 */

public class ToDoListDBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "Todolist";
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 4;

    public ToDoListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriesInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(ToDoListInfoEntry.SQL_CREATE_TABLE);
        Log.i(LOGTAG, "Tables created");

        db.execSQL("INSERT INTO category (categoryName) VALUES ('Home'), ('Work'), ('School'), ('Sports'), ('Family'), ('Hobbies')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL(CategoriesInfoEntry.SQL_DELETE_TABLE + CategoriesInfoEntry.TABLE_NAME);
            db.execSQL(ToDoListInfoEntry.SQL_DELETE_TABLE + ToDoListInfoEntry.TABLE_NAME);
            onCreate(db);
            Log.i(LOGTAG, "Database upgraded from " + oldVersion + " to " + newVersion);

        }
    }

    public Cursor getAllCategories() {

        SQLiteDatabase db = getReadableDatabase();
        String[] categoryColumns = {
                CategoriesInfoEntry.COLUMN_CATEGORY_NAME,
                CategoriesInfoEntry._ID
        };

        Cursor cursor = db.query(CategoriesInfoEntry.TABLE_NAME, categoryColumns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getToDos(int categoryId) {

        SQLiteDatabase db = getReadableDatabase();

        // SELECT todolistTitle FROM todolist INNER JOIN category ON todolistCategoryID = category._ID WHERE todolistCategoryId = categoryId;


        String query = "SELECT " + ToDoListInfoEntry.COLUMN_TODOLIST_TITLE +
                " FROM " + ToDoListInfoEntry.TABLE_NAME +
                " INNER JOIN " + CategoriesInfoEntry.TABLE_NAME +
                " ON " + ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID + " = " + CategoriesInfoEntry.TABLE_NAME + "." + CategoriesInfoEntry._ID +
                " WHERE " + ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID + " = " + categoryId;

        Cursor cursor = db.rawQuery(query, null);

        Log.i(LOGTAG, "Count todos per category: " + cursor.getCount());
        return cursor;


    }

    public void addToDo(String toDoTitle, String toDoDate, int toDoCategoryId) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE, toDoTitle);
        cv.put(ToDoListInfoEntry.COLUMN_TODOLIST_DATE, toDoDate);
        cv.put(ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID, toDoCategoryId);
        long newRowId = db.insert(ToDoListInfoEntry.TABLE_NAME, null, cv);

        Log.i(LOGTAG, "Todo: " + toDoTitle + " Date: " + toDoDate + " Category: " + toDoCategoryId + " Added to row " + newRowId);

    }
}
