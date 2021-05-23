package com.example.todo;

import android.util.Log;

import java.util.ArrayList;

public class ItemCollection{
    ArrayList<Item> collection;

    public ItemCollection()
    {
        collection = new ArrayList<>();
    }

    public Item getItem(int index)
    {
        return collection.get(index);
    }

    public void setItem(int index, Item i)
    {
        collection.set(index, i);
    }

    public void addItem(Item i)
    {
        collection.add(i);
    }

    public ArrayList<Item> getCollection() {
        return collection;
    }

    public void setCollection(ArrayList<Item> collection) {
        this.collection = collection;
    }

    public void loadCollection(String collString)
    {
        Log.d("ItemCollection", "loadCollection: "+collString);
        String items[] = collString.split(";");
        for (String item: items) {
            collection.add(Item.generateItem(item));
        }
    }

    @Override
    public String toString() {
        String collString = "";

        for (int i=0; i<collection.size(); i++)
        {
            collString += collection.get(i).toString()+";";
        }

        return collString;
    }

    public String toStringDisplay() {
        String collString = "";

        for (int i=0; i<collection.size(); i++)
        {
            collString += collection.get(i).toStringDisplay()+" ";
        }

        return collString;
    }
}
