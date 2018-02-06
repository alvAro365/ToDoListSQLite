package com.example.alvar.todolist;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditTodoActivity extends AppCompatActivity {

    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        displayCategoriesSpinner();
    }

    private void displayCategoriesSpinner() {
        Spinner categoriesSpinner = findViewById(R.id.spinner_edit_todo);
        ToDoListDBHelper toDoListDBHelper = new ToDoListDBHelper(this);
        Cursor categoriesCursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesCursorAdapter = new CategoriesCursorAdapter(this, categoriesCursor);
        categoriesSpinner.setAdapter(categoriesCursorAdapter);

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedItem = (int) id;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedItem = 1;

            }
        });
    }

    public void onSaveClick(View view) {

        Toast.makeText(this, "Save edit", Toast.LENGTH_SHORT).show();


    }
}
