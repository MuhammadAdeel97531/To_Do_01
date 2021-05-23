package com.example.todo;

import java.util.ArrayList;

public class TaskCollection {
    ArrayList<Task> collection;

    public TaskCollection() {
        collection = new ArrayList<>();
    }

    public Task getTask(int index)
    {
        return collection.get(index);
    }

    public void setItem(int index, Task i)
    {
        collection.set(index, i);
    }

    public void addItem(Task i)
    {
        collection.add(i);
    }

    public ArrayList<Task> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<Task> collection) {
        this.collection = collection;
    }
}
