package ru.mospolytech.lab1;

public class Auth {
    private String login;
    private String password;
    private String name;
    private String surname;
    private int id_user;

    public Auth(String login, String password, int id_user, String name, String surname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public int getId_user() {
        return id_user;
    }
}
