package com.project.spotify.entity;

public class Album {
    private String name;
    private String type;
    private String imageUrl;
    private String artist;

    public Album(String name, String type, String imageUrl, String artist) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.artist = artist;
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

    public String getArtist() {
        return artist;
    }
}