package com.example.alvar.todolist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.alvar.todolist.ToDoListDatabaseContract.*;

/**
 * Created by Alvar on 04/02/2018.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private Cursor mCursor;
    private int toDoTitlePos;

    public ToDoListAdapter(Context context, Cursor mCursor) {
        this.context = context;
        this.mCursor = mCursor;
        this.layoutInflater = LayoutInflater.from(this.context);

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if (this.mCursor == null) {
            return;
        }
        toDoTitlePos = mCursor.getColumnIndex(ToDoListInfoEntry.COLUMN_TODOLIST_TITLE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_to_do_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String toDo = mCursor.getString(toDoTitlePos);
        holder.toDoText.setText(toDo);

    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public final TextView toDoText;


        public ViewHolder(View itemView) {
            super(itemView);
            toDoText = itemView.findViewById(R.id.to_do_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(context, "Click registered", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, EditTodoActivity.class);
            context.startActivity(intent);
            
        }
    }
}
