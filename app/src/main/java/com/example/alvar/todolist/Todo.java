package com.example.alvar.todolist;

/**
 * Created by Alvar on 04/02/2018.
 */

public class Todo {

    private int toDoId;
    private String toDoTitle;
    private String toDoDate;
    private int toDoCategoryID;

    public Todo() {

    }

    public Todo(int toDoId, String toDoTitle, String toDoDate, int toDoCategoryID) {
        this.toDoId = toDoId;
        this.toDoTitle = toDoTitle;
        this.toDoDate = toDoDate;
        this.toDoCategoryID = toDoCategoryID;
    }

    public int getToDoId() {
        return toDoId;
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public String getToDoTitle() {
        return toDoTitle;
    }

    public void setToDoTitle(String toDoTitle) {
        this.toDoTitle = toDoTitle;
    }

    public String getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(String toDoDate) {
        this.toDoDate = toDoDate;
    }

    public int getToDoCategoryID() {
        return toDoCategoryID;
    }

    public void setToDoCategoryID(int toDoCategoryID) {
        this.toDoCategoryID = toDoCategoryID;
    }
}
