package com.project.spotify.additional;

public class Track {
    private String trackName;
    private String artistName;

    public Track(String trackName, String artistName) {
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
