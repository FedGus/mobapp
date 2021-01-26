package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SerializerPetitionDetail {

    @SerializedName("id_petition")
    int id_petition;

    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    @SerializedName("filename")
    String filename;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("address")
    String address;

    @SerializedName("timestamp")
    String timestamp;
}
