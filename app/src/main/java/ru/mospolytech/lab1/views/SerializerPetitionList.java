package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerializerPetitionList {
    @SerializedName("petition")
    List<SerializerPetitionDetail> all;
}
