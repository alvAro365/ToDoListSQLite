package com.example.alvar.todolist.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.UnicodeSetSpanner;
import android.util.Log;

import static com.example.alvar.todolist.Data.ToDoListDatabaseContract.*;

/**
 * Created by Alvar on 03/02/2018.
 */

public class ToDoListDBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "Todolist";
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 9;

    public ToDoListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriesInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(ToDoListInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(UserInfoEntry.SQL_CREATE_TABLE);
        Log.i(LOGTAG, "Tables created");

        db.execSQL("INSERT INTO category (categoryName) VALUES ('Home'), ('Work'), ('School'), ('Sports'), ('Family'), ('Hobbies')");
        db.execSQL("INSERT INTO users (userName) VALUES ('Alvar'),('Pernilla')");
        db.execSQL("INSERT INTO todolist (todolistTitle, todolistCategoryId, userId) VALUES " +
                "('Handla mat', 1, 1)," +
                "('Städa', 1, 2)," +
                "('Köpa ny tv', 1, 1)," +
                "('Lämna inn labb 2', 3, 1)," +
                "('Hämta Noah', 4, 2)," +
                "('Spela schack', 5, 1)," +
                "('Hitta studentjobb', 2, 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL(CategoriesInfoEntry.SQL_DELETE_TABLE + CategoriesInfoEntry.TABLE_NAME);
            db.execSQL(ToDoListInfoEntry.SQL_DELETE_TABLE + ToDoListInfoEntry.TABLE_NAME);
            db.execSQL(UserInfoEntry.SQL_DELETE_TABLE + UserInfoEntry.TABLE_NAME);
            onCreate(db);
            Log.i(LOGTAG, "Database upgraded from " + oldVersion + " to " + newVersion);

        }
    }

    public Cursor getAllCategories() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] categoryColumns = {
                CategoriesInfoEntry.COLUMN_CATEGORY_NAME,
                CategoriesInfoEntry._ID
        };

        Cursor cursor = db.query(CategoriesInfoEntry.TABLE_NAME, categoryColumns, null, null, null, null, null);
        return cursor;
    }

    //Get todos by category
    public Cursor getToDos(int categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT todolistTitle, todolistId FROM todolist INNER JOIN category ON todolistCategoryID = category._ID WHERE todolistCategoryId = categoryId;

        String query = "SELECT " + ToDoListInfoEntry.COLUMN_TODOLIST_TITLE + ", " +
                ToDoListInfoEntry.TABLE_NAME + "." +
                ToDoListInfoEntry._ID + ", " +
                ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID +
                " FROM " + ToDoListInfoEntry.TABLE_NAME +
                " INNER JOIN " + CategoriesInfoEntry.TABLE_NAME +
                " ON " + ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID + " = " + CategoriesInfoEntry.TABLE_NAME + "." + CategoriesInfoEntry._ID +
                " WHERE " + ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID + " = " + categoryId;

        Cursor cursor = db.rawQuery(query, null);

        //Log.i(LOGTAG, "Count todos per category: " + cursor.getCount());
        return cursor;

    }

    public Cursor getTodosByUser(int userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + ToDoListInfoEntry.COLUMN_TODOLIST_TITLE + ", " +
                ToDoListInfoEntry.TABLE_NAME + "." +
                ToDoListInfoEntry._ID + ", " + UserInfoEntry.TABLE_NAME + "." + UserInfoEntry._ID +
                " FROM " + ToDoListInfoEntry.TABLE_NAME +
                " INNER JOIN " + UserInfoEntry.TABLE_NAME +
                " ON " + ToDoListInfoEntry.COLUMN_TODOLIST_USER_ID +
                " = " + UserInfoEntry.TABLE_NAME + "." + UserInfoEntry._ID +
                " WHERE " + ToDoListInfoEntry.COLUMN_TODOLIST_USER_ID + " = " + userId;

        String[] columns = {ToDoListInfoEntry.TABLE_NAME + "." + ToDoListInfoEntry._ID, ToDoListInfoEntry.COLUMN_TODOLIST_TITLE};
        String selection = ToDoListInfoEntry.COLUMN_TODOLIST_TITLE + " = ? ";
        String[] selectionArgs = {String.valueOf(userId)};
        String table = ToDoListInfoEntry.TABLE_NAME + " INNER JOIN " + UserInfoEntry.TABLE_NAME +
                " ON " + ToDoListInfoEntry.COLUMN_TODOLIST_USER_ID + " = " +
                UserInfoEntry.TABLE_NAME + "." + UserInfoEntry._ID;

        Cursor cursor = db.rawQuery(query, null);
        Cursor cursor1 = db.query(table, columns, selection, selectionArgs, null, null, null);

        Log.i(LOGTAG, "Cursor: "+ cursor.getCount());

        return cursor;


    }


    public int getTodosCount(String userName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM "+ ToDoListInfoEntry.TABLE_NAME + " INNER JOIN " +
                UserInfoEntry.TABLE_NAME + " ON " + ToDoListInfoEntry.COLUMN_TODOLIST_USER_ID + " = " +
                UserInfoEntry.TABLE_NAME + "." + UserInfoEntry._ID + " WHERE " +
                UserInfoEntry.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = {userName};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        int amountOfTodos = cursor.getInt(0);

        Log.i(LOGTAG, "READ: Amount of todos: " + amountOfTodos);
        return amountOfTodos;

    }

    public void addToDo(String toDoTitle, int toDoCategoryId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE, toDoTitle);
        cv.put(ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID, toDoCategoryId);
        long newRowId = db.insert(ToDoListInfoEntry.TABLE_NAME, null, cv);

        if (newRowId > 0) {
            Log.i(LOGTAG, "CREATE: TodoTitle: " + toDoTitle + " Category: " + toDoCategoryId + " Added to row " + newRowId);
        }
    }

    public void deleteTodo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {String.valueOf(id)};
        String whereClause = ToDoListInfoEntry._ID + " = ?";

        long i = db.delete(ToDoListInfoEntry.TABLE_NAME, whereClause, selectionArgs);

        if (i > 0) {
            Log.i(LOGTAG, "DELETE: Amount of rows deleted: " + i);
        } else {
            Log.i(LOGTAG, "Error: Delete failed");
        }
    }

    public Cursor getAllToDos() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ToDoListInfoEntry.TABLE_NAME, null);

        return cursor;

    }

    public Cursor getTodoById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // SELECT * FROM todolist WHERE todolist._id = id;
        Cursor cursor = db.rawQuery("SELECT * FROM " + ToDoListInfoEntry.TABLE_NAME + " WHERE " +
                ToDoListInfoEntry.TABLE_NAME + "." + ToDoListInfoEntry._ID + "=" + id, null);
        return cursor;

    }

    public boolean updateTodoItem(long itemId, String editedTodo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(itemId)};
        String whereClause = ToDoListInfoEntry._ID + " = ?";
        ContentValues contentValues = new ContentValues();
        contentValues.put(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE, editedTodo);
        long i = db.update(ToDoListInfoEntry.TABLE_NAME, contentValues, whereClause, whereArgs);

        if (i > 0) {
            Log.i(LOGTAG, "UPDATE: Amount of rows updated " + i);
        }
        return i > 0;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userCursor = db.rawQuery("SELECT * FROM " + UserInfoEntry.TABLE_NAME, null);
        return userCursor;
    }


}
