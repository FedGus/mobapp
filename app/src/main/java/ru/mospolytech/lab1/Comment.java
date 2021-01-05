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
    int id_user;

    public Comment(int id_user, String content, String id_comment, int id_petition) {
        this.id_petition = id_petition;
        this.id_user = id_user;
        this.content = content;
        this.id_comment = id_comment;
    }
}
