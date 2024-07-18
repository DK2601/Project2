package com.project.spotify.entity;
public class Artist {
    private String name;
    private String type;
    private String imageUrl;

    public Artist(String name, String type, String imageUrl) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
