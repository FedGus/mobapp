package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentsList {
    @SerializedName("comment")
    List<Comment> all;
}
