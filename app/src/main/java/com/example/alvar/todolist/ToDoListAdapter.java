package com.example.alvar.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.alvar.todolist.ToDoListDatabaseContract.*;

/**
 * Created by Alvar on 04/02/2018.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private Cursor cursor;
    private int toDoTitlePos;


    public ToDoListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.layoutInflater = LayoutInflater.from(this.context);

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (this.cursor == null) {
            return;
        }
        toDoTitlePos = cursor.getColumnIndex(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_to_do_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String toDo = cursor.getString(toDoTitlePos);
        holder.toDoText.setText(toDo);

    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView toDoText;


        public ViewHolder(View itemView) {
            super(itemView);
            toDoText = itemView.findViewById(R.id.to_do_text);
        }
    }
}
