package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

public class CommentAuthor {
    @SerializedName("name")
    String name;

    @SerializedName("surname")
    String surname;
}
