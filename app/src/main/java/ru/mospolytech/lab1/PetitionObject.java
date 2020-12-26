package ru.mospolytech.lab1;

public class PetitionObject {
    public String id;
    public String object;

    public PetitionObject(String id, String object) {
        this.object = object;
        this.id = id;
    }

    @Override
    public String toString() {
        return object;
    }
}
