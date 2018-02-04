package com.example.alvar.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private ToDoListDBHelper toDoListDBHelper;
    private int selectedItemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toDoListDBHelper = new ToDoListDBHelper(this);

        displayCategoriesSpinner();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateToDoActivity();
            }
        });
    }

    private void displayCategoriesSpinner() {
        Spinner categoriesSpinner = findViewById(R.id.spinner_categories);
        Cursor cursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesAdapter = new CategoriesCursorAdapter(this, cursor);
        categoriesSpinner.setAdapter(categoriesAdapter);

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selectedItemId = (int) id;
                toDoListDBHelper.getToDos(selectedItemId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                selectedItemId = 1;
                toDoListDBHelper.getToDos(selectedItemId);

            }
        });

    }

    private void startCreateToDoActivity() {

        Intent intent = new Intent(MainActivity.this, CreateToDoActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
