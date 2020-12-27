package ru.mospolytech.lab1;

import android.app.Application;

public class App extends Application {
    private int id;
    private String name;
    private String surname;
    private String login;

    public int getIdState(){
        return id;
    }

    public String getNameState(){
        return name;
    }

    public String getSurnameState(){
        return surname;
    }

    public String getLoginState(){
        return login;
    }

    public int setState(int id_user, String name_user, String surname_user, String login_user){
        id = id_user;
        name = name_user;
        surname = surname_user;
        login = login_user;
        return id;
    }
}
