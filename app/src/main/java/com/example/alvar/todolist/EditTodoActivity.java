package com.example.alvar.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alvar.todolist.ToDoListDatabaseContract.ToDoListInfoEntry;

public class EditTodoActivity extends AppCompatActivity {

    private int selectedItem;
    private ToDoListDBHelper toDoListDBHelper;
    private long itemID;
    private String todoItem;
    private EditText editTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        toDoListDBHelper = new ToDoListDBHelper(this);
        displayCategoriesSpinner();
        getTodoItem();
    }

    private void getTodoItem() {
        Intent intent = getIntent();
        itemID = intent.getLongExtra("todo", 0);
        Log.i("Todolist", "Item with id: " + itemID + " received");
        Cursor todoItemCursor = toDoListDBHelper.getTodoById(itemID);
        todoItemCursor.moveToFirst();
        todoItem = todoItemCursor.getString(todoItemCursor.getColumnIndex(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE));
        Log.i("Todolist", "The item title is: " + todoItem);

        setViews(todoItem);
    }

    private void setViews(String toDoItem) {
        editTodo = findViewById(R.id.edit_to_do_edit);
        editTodo.setText(toDoItem);
        int position = editTodo.length();
        Editable editableText = editTodo.getText();
        Selection.setSelection(editableText, position);

    }

    private void displayCategoriesSpinner() {
        Spinner categoriesSpinner = findViewById(R.id.spinner_edit_todo);
        ToDoListDBHelper toDoListDBHelper = new ToDoListDBHelper(this);
        Cursor categoriesCursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesCursorAdapter = new CategoriesCursorAdapter(this, categoriesCursor);
        categoriesSpinner.setAdapter(categoriesCursorAdapter);
        categoriesSpinner.setSelection(getSpinnerSelection());

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

    private int getSpinnerSelection() {
        return getIntent().getIntExtra("categoryId", 0) -1;
    }

    public void onSaveClick(View view) {
        String editedTodo = editTodo.getText().toString();
        if (toDoListDBHelper.updateTodoItem(itemID, editedTodo)) {
            Toast.makeText(this, "Save edit", Toast.LENGTH_SHORT).show();

            Log.i("Todolist", "Update itemId: " + itemID + " new todo: " + editedTodo);
            Intent backToMainActivity = new Intent(this, MainActivity.class);
            backToMainActivity.putExtra("categoryId", getSpinnerSelection());
            backToMainActivity.putExtra("source", "EditTodoActivity");
            backToMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            backToMainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(backToMainActivity);

        } else {

            Toast.makeText(this, "Update error", Toast.LENGTH_SHORT).show();

        }
    }
}
