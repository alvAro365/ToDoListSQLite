package com.example.alvar.todolist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Alvar on 06/02/2018.
 */

public class StatisticsCursorAdapter extends CursorAdapter {
    ToDoListDBHelper toDoListDBHelper;

    public StatisticsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        toDoListDBHelper = new ToDoListDBHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.statistics_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textCategory = view.findViewById(R.id.category_item);
        TextView textAmountTodos = view.findViewById(R.id.text_todos_amount);
        String category = cursor.getString(cursor.getColumnIndex(ToDoListDatabaseContract.CategoriesInfoEntry.COLUMN_CATEGORY_NAME));
        int categoryId = cursor.getInt(cursor.getColumnIndex(ToDoListDatabaseContract.CategoriesInfoEntry._ID));
        Cursor categoryCursor = toDoListDBHelper.getToDos(categoryId);
        int amountOfTodos = categoryCursor.getCount();
        textCategory.setText(category);
        textAmountTodos.setText(String.valueOf(amountOfTodos));

    }
}
