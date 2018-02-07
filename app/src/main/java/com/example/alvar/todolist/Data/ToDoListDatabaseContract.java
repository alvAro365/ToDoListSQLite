package com.example.alvar.todolist.Data;

import android.provider.BaseColumns;

/**
 * Created by Alvar on 03/02/2018.
 */

public final class ToDoListDatabaseContract {

    private ToDoListDatabaseContract() {}; // makes the class non-creatable


    public static final class ToDoListInfoEntry implements BaseColumns {

        public static final String TABLE_NAME = "todolist";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TODOLIST_TITLE = "todolistTitle";
        public static final String COLUMN_TODOLIST_CATEGORY_ID = "todolistCategoryId";
        public static final String COLUMN_TODOLIST_USER_ID = "userId";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ";

        // CREATE TABLE todolist (todolistID INTEGER PRIMARY KEY, todolistTitle TEXT, todolistDate TEXT, todolistCategoryID INT)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TODOLIST_TITLE + " TEXT, " +
                        COLUMN_TODOLIST_CATEGORY_ID + " INTEGER, " +
                        COLUMN_TODOLIST_USER_ID + " INTEGER, " +
                        " FOREIGN KEY(" + ToDoListInfoEntry.COLUMN_TODOLIST_CATEGORY_ID + ") REFERENCES " + CategoriesInfoEntry.TABLE_NAME +
                        "(" + CategoriesInfoEntry._ID + ")" +
                        " FOREIGN KEY(" + ToDoListInfoEntry.COLUMN_TODOLIST_USER_ID + ") REFERENCES " + UserInfoEntry.TABLE_NAME +
                        "(" + UserInfoEntry._ID + ")" +
                        ")";
    }

    public static final class CategoriesInfoEntry implements BaseColumns {
        //Category table
        public static final String TABLE_NAME = "category";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CATEGORY_NAME = "categoryName";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ";


        // CREATE TABLE category (COLUMN_CATEGORY_ID INTEGER PRIMARY KEY, COLUMN_CATEGORY_NAME TEXT);
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_CATEGORY_NAME + " TEXT" +
                        ")";
    }

    public static final class UserInfoEntry implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String _ID = BaseColumns._ID;
        public static final String  COLUMN_USER_NAME = "userName";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ";

        // CREATE TABLE users (BaseColumns._ID INTEGER PRIMARY KEY, username TEXT);
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_USER_NAME + " TEXT" +
                        ")";
    }
}
