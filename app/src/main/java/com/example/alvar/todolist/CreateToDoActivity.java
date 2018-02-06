package com.example.alvar.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateToDoActivity extends AppCompatActivity {

    private int selectedItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_to_do);
        displayCategoriesSpinner();
    }

    private void displayCategoriesSpinner() {
        Spinner categoriesSpinner = findViewById(R.id.spinner_categories);
        ToDoListDBHelper toDoListDBHelper = new ToDoListDBHelper(this);
        final Cursor cursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesAdapter = new CategoriesCursorAdapter(this, cursor);
        categoriesSpinner.setAdapter(categoriesAdapter);
        categoriesSpinner.setSelection(getSpinnerPosition());

        //Log.i("Todolist", "Spinnerselection received is nr: " + getSpinnerPosition());


        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selectedItemID = (int) id;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                selectedItemID = 1;

            }
        });

    }

    private int getSpinnerPosition() {
        return getIntent().getIntExtra("selectedCategory", 0) - 1;
    }

    public void saveToDo(View view) {
        ToDoListDBHelper dbHelper = new ToDoListDBHelper(this);
        EditText editToDo = findViewById(R.id.editToDo);
        EditText editDate = findViewById(R.id.editDate);
        String toDo = editToDo.getText().toString();
        String toDoDate = editDate.getText().toString();

        if (toDo.isEmpty()) {
            Toast.makeText(this, "Error: No text found!", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.addToDo(toDo, toDoDate, selectedItemID);
            Intent intent = new Intent(CreateToDoActivity.this, MainActivity.class);
            intent.putExtra("category", getSpinnerPosition());
            intent.putExtra("source", "CreateTodoActivity");
            startActivity(intent);
        }
    }
}
