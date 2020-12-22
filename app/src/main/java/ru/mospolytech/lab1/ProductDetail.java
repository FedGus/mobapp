package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ProductDetail {

    @SerializedName("id_petition")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("image")
    String image;

//    @SerializedName("location")
//    Locations location;

    @SerializedName("timestamp")
    String timestamp;
}
