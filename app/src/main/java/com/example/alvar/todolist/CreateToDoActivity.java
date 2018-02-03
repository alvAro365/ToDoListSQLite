package com.example.alvar.todolist;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class CreateToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_to_do);

        displayCategoriesSpinner();

    }


    private void displayCategoriesSpinner() {
        Spinner categoriesSpinner = findViewById(R.id.spinner_categories);
        ToDoListDBHelper toDoListDBHelper = new ToDoListDBHelper(this);
        Cursor cursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesAdapter = new CategoriesCursorAdapter(this, cursor);
        categoriesSpinner.setAdapter(categoriesAdapter);

    }
}
