package ru.mospolytech.lab1.classes;

public class Category {
    public String id;
    public String category;

    public Category(String id, String category) {
        this.category = category;
        this.id = id;
    }

    @Override
    public String toString() {
        return category;
    }
}
