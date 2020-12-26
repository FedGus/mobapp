package ru.mospolytech.lab1;

public class Recipient {
    public String id;
    public String recipient;

    public Recipient(String id, String recipient) {
        this.recipient = recipient;
        this.id = id;
    }

    @Override
    public String toString() {
        return recipient;
    }
}
