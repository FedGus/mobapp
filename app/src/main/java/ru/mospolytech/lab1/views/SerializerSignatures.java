package ru.mospolytech.lab1.views;

import com.google.gson.annotations.SerializedName;

public class SerializerSignatures {
    @SerializedName("countSignatures")
    String countSignatures;

    public SerializerSignatures(int id_petition, int id_user) {
        this.id_petition = id_petition;
        this.id_user = id_user;
    }

    @SerializedName("id_petition")
    int id_petition;

    @SerializedName("id_user")
    int id_user;
}
