package com.project.spotify.entity;

// Step 2: Create a new class `Album`
public class Album {
    private String name;
    private String artist;
    private String imageUrl;

    public Album(String name, String artist, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
