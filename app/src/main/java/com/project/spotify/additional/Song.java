package com.project.spotify.additional;

public class Song {
    private String title;
    private String album;

    public Song(String title, String album) {
        this.title = title;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}

