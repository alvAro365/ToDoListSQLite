package com.example.alvar.todolist.Activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.alvar.todolist.Adapters.StatisticsCursorAdapter;
import com.example.alvar.todolist.Data.ToDoListDBHelper;
import com.example.alvar.todolist.R;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        showStatistics();
    }

    private void showStatistics() {
        ToDoListDBHelper toDoListDBHelper = new ToDoListDBHelper(this);
        Cursor categoriesCursor = toDoListDBHelper.getAllCategories();
        ListView listItems = findViewById(R.id.list_statistics);
        StatisticsCursorAdapter statisticsCursorAdapter = new StatisticsCursorAdapter(this,categoriesCursor);
        listItems.setAdapter(statisticsCursorAdapter);
    }
}
