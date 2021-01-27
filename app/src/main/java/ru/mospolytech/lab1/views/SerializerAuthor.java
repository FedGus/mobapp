package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

public class SerializerAuthor {
    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;
}
