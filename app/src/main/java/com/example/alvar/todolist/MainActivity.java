package com.example.alvar.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.alvar.todolist.ToDoListDatabaseContract.*;

public class MainActivity extends AppCompatActivity {
    private ToDoListDBHelper toDoListDBHelper;
    private int selectedItemId;
    private RecyclerView recyclerView;
    private ToDoListAdapter toDoListAdapter;
    private Spinner categoriesSpinner;
    Cursor categoryCursor;
    private Menu menu;

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
        categoriesSpinner = findViewById(R.id.spinner_categories);
        Cursor cursor = toDoListDBHelper.getAllCategories();
        CategoriesCursorAdapter categoriesAdapter = new CategoriesCursorAdapter(this, cursor);
        categoriesSpinner.setAdapter(categoriesAdapter);

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                selectedItemId = (int) id;
                categoryCursor = toDoListDBHelper.getToDos(selectedItemId);
                showToDos(categoryCursor);
                setupTouch();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                selectedItemId = 0;
                toDoListDBHelper.getToDos(selectedItemId);
            }
        });

    }

    private void setupTouch() {
        setupItemTouchHelper();

    }

    private void showToDos(Cursor categoryCursor) {
        recyclerView = findViewById(R.id.recyclerview_toDo);
        LinearLayoutManager toDosLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(toDosLayoutManager);
        toDoListAdapter = new ToDoListAdapter(this, categoryCursor);
        recyclerView.setAdapter(toDoListAdapter);

    }



    private void setupItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                categoryCursor.moveToPosition(position);
                long id =  categoryCursor.getLong(categoryCursor.getColumnIndex(ToDoListInfoEntry._ID));
                toDoListDBHelper.deleteTodo(id);
                categoryCursor = toDoListDBHelper.getToDos(selectedItemId);
                showToDos(categoryCursor);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void startCreateToDoActivity() {

        Intent toCreateTodo = new Intent(MainActivity.this, CreateToDoActivity.class);
        toCreateTodo.putExtra("selectedCategory", selectedItemId);
        startActivity(toCreateTodo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        loadUserMenu();
        return true;
    }

    private void loadUserMenu() {
        Cursor userCursor = toDoListDBHelper.getAllUsers();
        String userName;
        int i = 0;
        MenuItem menuItem = menu.findItem(R.id.action_user);
        if (userCursor != null) {
            while (userCursor.moveToNext()) {
                i++;
                userName = userCursor.getString(userCursor.getColumnIndex(UserInfoEntry.COLUMN_USER_NAME));
                menuItem.getSubMenu().add(Menu.NONE, i, i, userName);
            }
            Log.i("Todolist", "Amount of users: " + userCursor.getCount());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_statistics) {
            Intent toStatistics = new Intent(this, StatisticsActivity.class);
            startActivity(toStatistics);
        } else if (id == R.id.action_user) {
            Toast.makeText(this, "User clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String source = getIntent().getStringExtra("source");
        if (source != null) {
            if (source.equals("CreateTodoActivity")) {
                categoriesSpinner.setSelection(getIntent().getIntExtra("category", 0));
            } else {
                categoriesSpinner.setSelection(getIntent().getIntExtra("categoryId", 0));
            }
        }
    }
}
