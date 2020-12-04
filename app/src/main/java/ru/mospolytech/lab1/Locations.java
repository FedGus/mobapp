package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

public class Locations {
    @SerializedName("description")
    String description;

    @SerializedName("latitude")
    double latitude;

    @SerializedName("longitude")
    double longitude;
}
