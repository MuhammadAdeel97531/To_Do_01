package com.example.todo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorage {
    File file;
    BufferedWriter bWritter;
    BufferedReader bReader;
    final static String FILENAME = "data.txt";

    public FileStorage(Context context)
    {
        file = new File(context.getFilesDir()+FILENAME);
    }

    public Boolean addRecord(Task task)
    {
        try {
            bWritter = new BufferedWriter(new FileWriter(file, true));
            bWritter.write(task.toString());
            bWritter.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean updateCollection(TaskCollection collection)
    {
        try {
            bWritter = new BufferedWriter(new FileWriter(file));
            for (int i=0; i<collection.getCollection().size(); i++)
            {
                bWritter.write(collection.getTask(i).toString());
            }
            bWritter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateCollection(int index, Task t)
    {
        TaskCollection temp = new TaskCollection();
        loadCollection(temp);
        temp.getCollection().set(index, t);
        updateCollection(temp);
    }

    public Boolean loadCollection(TaskCollection collection)
    {
        try {
            bReader = new BufferedReader(new FileReader(file));
            String line = bReader.readLine();

            while(line != null)
            {
                collection.getCollection().add(Task.generateTask(line));
                line = bReader.readLine();
            }

            bReader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
