package com.muhaiminur.firebase;

public class Image {
    String id;
    String image_description;
    String image_url;
    String love_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_description() {
        return image_description;
    }

    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_count() {
        return love_count;
    }

    public void setImage_count(String image_count) {
        this.love_count = image_count;
    }



    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", image_description='" + image_description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", image_count='" + love_count + '\'' +
                '}';
    }
}
