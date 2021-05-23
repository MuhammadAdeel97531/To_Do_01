package com.example.todo;

public class Item {
    String name;
    Boolean status;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
        this.status = false;
    }

    public Item(String name, Boolean status) {
        this.name = name;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public static Item generateItem(String itemString) {
        String str[] = itemString.split(":");
        return new Item(str[0], Boolean.parseBoolean(str[1]));
    }

    @Override
    public String toString() {
        return this.name+":"+status+":";
    }

    public String toStringDisplay() {
        return this.name;
    }
}
