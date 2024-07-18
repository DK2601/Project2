package com.project.spotify.entity;

public class Track {
    private String name;
    private String artist;

    public Track(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }
}
