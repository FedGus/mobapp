package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

public class SerializerUser {
    @SerializedName("login")
    String login;

    @SerializedName("password")
    String password;

    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;
}
