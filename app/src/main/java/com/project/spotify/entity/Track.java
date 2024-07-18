package com.project.spotify.entity;

public class Track {
    private String name;
    private String type;
    private String artistName;
    private String imageUrl;

    public Track(String name, String type, String artistName, String imageUrl) {
        this.name = name;
        this.type = type;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
