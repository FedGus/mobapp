package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id_petition")
    int id_petition;

    @SerializedName("content")
    String content;

    @SerializedName("id_comment")
    String id_comment;

    @SerializedName("id_user")
    String id_user;

}
