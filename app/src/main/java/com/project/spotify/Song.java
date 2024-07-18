package com.project.spotify;

public class Song {
    private String nameSong;
    private String nameGroup;
    private String imageSong;
    private String linkSong;

    public Song(String nameSong, String nameGroup, String imageSong, String linkSong) {
        this.nameSong = nameSong;
        this.nameGroup = nameGroup;
        this.imageSong = imageSong;
        this.linkSong = linkSong;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getImageSong() {
        return imageSong;
    }

    public void setImageSong(String imageSong) {
        this.imageSong = imageSong;
    }

    public String getLinkSong() {
        return linkSong;
    }

    public void setLinkSong(String linkSong) {
        this.linkSong = linkSong;
    }
}

