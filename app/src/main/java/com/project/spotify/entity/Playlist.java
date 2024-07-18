package com.project.spotify.entity;

public class Playlist {
    private String name;
    private String type;
    private String imageUrl;

    public Playlist(String name, String type, String imageUrl) {
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
