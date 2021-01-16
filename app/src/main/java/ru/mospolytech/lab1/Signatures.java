package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

public class Signatures {
    @SerializedName("countSignatures")
    String countSignatures;

    public Signatures(int id_petition, int id_user) {
        this.id_petition = id_petition;
        this.id_user = id_user;
    }

    @SerializedName("id_petition")
    int id_petition;

    @SerializedName("id_user")
    int id_user;
}
