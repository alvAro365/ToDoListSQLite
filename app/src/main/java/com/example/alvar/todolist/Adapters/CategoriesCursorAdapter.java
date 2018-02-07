package com.example.alvar.todolist.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.alvar.todolist.R;

import static com.example.alvar.todolist.Data.ToDoListDatabaseContract.*;

/**
 * Created by Alvar on 03/02/2018.
 */

public class CategoriesCursorAdapter extends CursorAdapter {

    public CategoriesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.spinner_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView spinnerItemView = view.findViewById(R.id.spinner_item);
        String category = cursor.getString(cursor.getColumnIndex(CategoriesInfoEntry.COLUMN_CATEGORY_NAME));
        spinnerItemView.setText(category);



    }
}
