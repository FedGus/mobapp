package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerializerCommentsList {
    @SerializedName("comment")
    List<SerializerComment> all;
}
