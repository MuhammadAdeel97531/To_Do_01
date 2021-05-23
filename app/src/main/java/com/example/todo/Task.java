package com.example.todo;

public class Task {
    String name;
    ItemCollection collection;
    String time;
    String date;
    int priority;
    Boolean isDone;
    Boolean repeat;

    public Task(String name, ItemCollection collection, String time) {
        this.name = name;
        this.collection = collection;
        this.time = time;
    }

    public Task(String name, ItemCollection collection, String time, String date, int priority, Boolean repeat, Boolean isDone) {
        this.name = name;
        this.collection = collection;
        this.time = time;
        this.date = date;
        this.priority = priority;
        this.repeat = repeat;
        this.isDone = isDone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCollection getItemCollection() {
        return collection;
    }

    public void setCollection(ItemCollection collection) {
        this.collection = collection;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public static Task generateTask(String taskString)
    {
        String attr[] = taskString.split(",");
        ItemCollection collection= new ItemCollection();
        collection.loadCollection(attr[1]);
        return new Task(attr[0], collection, attr[2], attr[3], Integer.parseInt(attr[4]), Boolean.parseBoolean(attr[5]), Boolean.parseBoolean(attr[6]));
    }

    @Override
    public String toString() {
        return this.name+","+this.collection.toString()+","+this.time+","+this.date+","+this.priority+","+this.repeat+","+this.isDone+"\n";
    }
}
