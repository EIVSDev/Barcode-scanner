package com.dev.eivs.testscan;

public class ProductModel {
    private String description;
    private int id;
    private String image;

    public ProductModel() {

    }

    public ProductModel(String image) {
        this.image = image;
    }

    public ProductModel(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public ProductModel(int id, String image, String description) {
        this.description = description;
        this.id = id;
        this.image = image;
    }
///////////////////////////////////////


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return image;
    }

    public void setUrl(String image) {
        this.image = image;
    }


}
