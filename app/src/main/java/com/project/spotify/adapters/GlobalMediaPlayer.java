package com.project.spotify.adapters;

import android.media.MediaPlayer;

import java.io.IOException;

public class GlobalMediaPlayer {
    private static GlobalMediaPlayer instance;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private String currentTrackUrl = "";
    private int currentPosition = 0;

    private GlobalMediaPlayer() {}

    public static synchronized GlobalMediaPlayer getInstance() {
        if (instance == null) {
            instance = new GlobalMediaPlayer();
        }
        return instance;
    }

    public void playSong(String url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                isPlaying = true;
            });
            currentTrackUrl = url;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPosition = mediaPlayer.getCurrentPosition();
            isPlaying = false;
        }
    }

    public void resumeSong() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && isPlaying) {
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public String getCurrentTrackUrl() {
        return currentTrackUrl;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
