package ru.mospolytech.lab1.classes;

public class Auth {
    private String login;
    private String password;
    private String name;
    private String surname;
    private int id_user;
    private int role;

    public Auth(String login, String password, int id_user, String name, String surname, int role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id_user = id_user;
        this.role = role;
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
