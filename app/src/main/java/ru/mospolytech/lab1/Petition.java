package ru.mospolytech.lab1;

public class Petition {
    private String title;
    private String image;
    private String content;
    private int id_category;
    private int id_object;
    private int id_status;
    private int id_user;
    private String latitude;
    private String longitude;
    private String address;

    public Petition(String title, String image, String content, int id_category, int id_object, int id_status, int id_user, String latitude, String longitude, String address) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.id_category = id_category;
        this.id_object = id_object;
        this.id_status = id_status;
        this.id_user = id_user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public int getId_category() {
        return id_category;
    }

    public int getId_object() {
        return id_object;
    }

    public int getId_status() {
        return id_status;
    }

    public int getId_user() {
        return id_user;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
