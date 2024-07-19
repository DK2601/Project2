package com.project.spotify.ui.playback;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.AudioManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.project.spotify.R;
import com.project.spotify.Song;
import com.project.spotify.PlaybackRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaybackFragment extends Fragment {
    private ImageButton btnPlayPause, btnNext, btnPre;
    private List<Song> songList;
    private int position = 0;
    private boolean repeat = false;
    private boolean checkRandom = false;
    private MediaPlayer mediaPlayer;
    private ImageView imgSong;
    private TextView tvName, tvNameGroup;
    private SeekBar songProgress, volumeControl;
    private AudioManager audioManager;
    private String previewUrl;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        initializeVolumeControl();
        initializeSongList();
        setupListeners();
        fetchPlaybackInfo();

        TextView title = view.findViewById(R.id.song_title);
        TextView singler = view.findViewById(R.id.song_artist);
    }

    private void fetchPlaybackInfo() {
        PlaybackRequest playbackRequest = new PlaybackRequest(getContext());
        playbackRequest.fetchTrack(new PlaybackRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                updateUIWithPlaybackInfo(response);
            }

            @Override
            public void onError(VolleyError error) {
                // Handle error
                Log.e("PlaybackFragment", "Error fetching playback info", error);
            }
        });
    }

    private void updateUIWithPlaybackInfo(JSONObject playbackInfo) {
        try {
            // Check if playbackInfo has "artists" and it's an array
            if (playbackInfo.has("artists") && playbackInfo.get("artists") instanceof JSONArray) {
                JSONArray artistsArray = playbackInfo.getJSONArray("artists");
                JSONObject album = playbackInfo.getJSONObject("album");
                String playlist = album.getString("name");
                // Assuming you want the first artist's name if there are multiple
                if (artistsArray.length() > 0) {
                    JSONObject firstArtist = artistsArray.getJSONObject(0);
                    String artistName = firstArtist.getString("name");
                    // Now you have the artist name, you can proceed with other data extraction
                    String songName = playbackInfo.getString("name");
                    String imageUrl = playbackInfo.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");
                    previewUrl = playbackInfo.getString("preview_url"); // Extract preview_url

                    tvName.setText(songName);
                    tvNameGroup.setText(artistName);
                    Glide.with(this).load(imageUrl).into(imgSong);

                    TextView playlistTextView = getView().findViewById(R.id.playlist);
                    playlistTextView.setText(playlist); // Set the text to the TextView
                }
            }
        } catch (JSONException e) {
            Log.e("PlaybackFragment", "Error parsing playback info", e);
        }

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    // Initialize mediaPlayer and play the song
                    playSong(previewUrl); // Assuming playSong is your method to play a song
                    btnPlayPause.setImageResource(R.drawable.ic_launcher_foreground); // Update to pause icon
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // Pause the currently playing track
                    btnPlayPause.setImageResource(R.drawable.ic_play_foreground); // Update to play icon
                } else {
                    mediaPlayer.start(); // Resume the song from the last pause position
                    btnPlayPause.setImageResource(R.drawable.ic_launcher_foreground); // Update to pause icon
                }
            }
        });    }

    private void initializeViews(View view) {
        btnNext = view.findViewById(R.id.btn_next);
        btnPre = view.findViewById(R.id.btn_pre);
        imgSong = view.findViewById(R.id.album_artwork);
        tvName = view.findViewById(R.id.song_title);
        tvNameGroup = view.findViewById(R.id.song_artist);
        btnPlayPause = view.findViewById(R.id.btn_play_pause);
        songProgress = view.findViewById(R.id.song_progress);
        volumeControl = view.findViewById(R.id.volume_control);

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btnPlayPause.setImageResource(R.drawable.ic_launcher_foreground); // Assuming ic_play is the play icon
                    } else {
                        mediaPlayer.start();
                        btnPlayPause.setImageResource(R.drawable.ic_play_foreground); // Assuming ic_pause is the pause icon
                    }
                }
            }
        });
    }

    private void setupListeners() {
        btnNext.setOnClickListener(view -> {
            if (songList.size() > 0) {
                if (mediaPlayer != null && (mediaPlayer.isPlaying() || mediaPlayer != null)) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position < songList.size()) {
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    position++;
                    if (repeat) {
                        if (position == 0) {
                            position = songList.size();
                        }
                        position -= 1;
                    }
                    if (checkRandom) {
                        Random random = new Random();
                        int iRandom = random.nextInt(songList.size());
                        if (iRandom == position) {
                            position = iRandom - 1;
                        }
                        position = iRandom;
                    }
                    if (position > songList.size() - 1) {
                        position = 0;
                    }
                    Glide.with(this)
                            .load(songList.get(position).getImageSong())
                            .into(imgSong);
                    tvName.setText(songList.get(position).getNameSong());
                    tvNameGroup.setText(songList.get(position).getNameGroup());
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    playSong(songList.get(position).getLinkSong());
                    updateTime();
                }
            }

            btnPre.setClickable(false);
            btnNext.setClickable(false);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                btnPre.setClickable(true);
                btnNext.setClickable(true);
            }, 5000);
        });

        btnPre.setOnClickListener(view -> {
            if (songList.size() > 0) {
                if (mediaPlayer != null && (mediaPlayer.isPlaying() || mediaPlayer != null)) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if (position > 0) {
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    position--;
                    if (repeat) {
                        if (position == songList.size() - 1) {
                            position = -1;
                        }
                        position += 1;
                    }
                    if (checkRandom) {
                        Random random = new Random();
                        int iRandom = random.nextInt(songList.size());
                        if (iRandom == position) {
                            position = iRandom + 1;
                        }
                        position = iRandom;
                    }
                    if (position < 0) {
                        position = songList.size() - 1;
                    }
                    Glide.with(this)
                            .load(songList.get(position).getImageSong())
                            .into(imgSong);
                    tvName.setText(songList.get(position).getNameSong());
                    tvNameGroup.setText(songList.get(position).getNameGroup());
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    playSong(songList.get(position).getLinkSong());
                    updateTime();
                }
            }

            btnPre.setClickable(false);
            btnNext.setClickable(false);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                btnPre.setClickable(true);
                btnNext.setClickable(true);
            }, 5000);
        });
    }

    private List<Song> initializeSongList() {
        List<Song> songs = new ArrayList<>();
        // Thêm các bài hát vào danh sách
        songs.add(new Song("Song 1", "Artist 1", "https://example.com/image1.jpg", "https://example.com/song1.mp3"));
        songs.add(new Song("Song 2", "Artist 2", "https://example.com/image2.jpg", "https://example.com/song2.mp3"));
        // Tiếp tục thêm các bài hát khác
        return songs;
    }

    private void playSong(String url) {
        // Check if mediaPlayer is already playing a track
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop(); // Stop the currently playing track
                mediaPlayer.release(); // Release resources
                mediaPlayer = null; // Set mediaPlayer to null
            } else {
                mediaPlayer.release(); // If not playing, just release resources
                mediaPlayer = null; // Set mediaPlayer to null
            }
        }
        // Initialize mediaPlayer for the new track
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync(); // Use prepareAsync to stream the audio
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start(); // Start playback
                    int duration = mp.getDuration(); // Get the duration of the audio
                    songProgress.setMax(duration); // Set the maximum value of the SeekBar
                    updateTime(); // Start updating the SeekBar progress
                }
            });
        } catch (Exception e) {
            Log.e("PlaybackFragment", "Error playing song", e);
        }
    }

    private void savePlaybackState(boolean isPlaying, String trackUrl) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isPlaying", isPlaying);
        editor.putString("trackUrl", trackUrl);
        editor.apply();
    }

    private PlaybackState getSavedPlaybackState() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        boolean isPlaying = sharedPreferences.getBoolean("isPlaying", false);
        String trackUrl = sharedPreferences.getString("trackUrl", "");
        return new PlaybackState(isPlaying, trackUrl);
    }

    private static class PlaybackState {
        boolean isPlaying;
        String trackUrl;

        PlaybackState(boolean isPlaying, String trackUrl) {
            this.isPlaying = isPlaying;
            this.trackUrl = trackUrl;
        }
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    songProgress.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void initializeVolumeControl() {
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not used
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not used
            }
        });
    }
}
