package com.example.geims_navigation_2.Domain;

import android.graphics.Picture;

public class CategoryModel {
    private int Id;
    private String Name;
    private String Picture;

    public CategoryModel(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
