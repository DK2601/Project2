package com.project.spotify;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayBack extends AppCompatActivity {
    private ImageButton btnPlayPause, btnNext, btnPre;
    private List<Song> songList;
    private int position = 0;
    private boolean repeat = false;
    private boolean checkRandom = false;
    private MediaPlayer mediaPlayer;
    private ImageView imgSong;
    private TextView tvName, tvNameGroup;
    private SeekBar songProgress, volumeControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_playback);
    }

//    protected void onCreated(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_playback);
//        // Nhận dữ liệu từ Intent
//        Intent intent = getIntent();
//        String songTitle = intent.getStringExtra("SONG_TITLE");
//        String songArtist = intent.getStringExtra("SONG_ARTIST");
//        String songUrl = intent.getStringExtra("SONG_URL");
//        String songImageUrl = intent.getStringExtra("SONG_IMAGE_URL");
//
//        songList = initializeSongList();
//
//        btnNext = findViewById(R.id.btn_next);
//        btnPre = findViewById(R.id.btn_pre);
//        imgSong = findViewById(R.id.album_artwork);
//        tvName = findViewById(R.id.song_title);
//        tvNameGroup = findViewById(R.id.song_artist);
//        btnPlayPause = findViewById(R.id.btn_play_pause);
//        songProgress = findViewById(R.id.song_progress);
//        volumeControl = findViewById(R.id.volume_control);
//
//        btnNext.setOnClickListener(view -> {
//            if (songList.size() > 0) {
//                if (mediaPlayer != null && (mediaPlayer.isPlaying() || mediaPlayer != null)) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                }
//                if (position < songList.size()) {
//                    btnPlayPause.setImageResource(R.drawable.ic_pause);
//                    position++;
//                    if (repeat) {
//                        if (position == 0) {
//                            position = songList.size();
//                        }
//                        position -= 1;
//                    }
//                    if (checkRandom) {
//                        Random random = new Random();
//                        int iRandom = random.nextInt(songList.size());
//                        if (iRandom == position) {
//                            position = iRandom - 1;
//                        }
//                        position = iRandom;
//                    }
//                    if (position > songList.size() - 1) {
//                        position = 0;
//                    }
//                    Glide.with(this)
//                            .load(songList.get(position).getImageSong())
//                            .into(imgSong);
//                    tvName.setText(songList.get(position).getNameSong());
//                    tvNameGroup.setText(songList.get(position).getNameGroup());
//                    btnPlayPause.setImageResource(R.drawable.ic_pause);
//                    playSong(songList.get(position).getLinkSong());
//                    updateTime();
//                }
//            }
//
//            btnPre.setClickable(false);
//            btnNext.setClickable(false);
//            Handler handler = new Handler();
//            handler.postDelayed(() -> {
//                btnPre.setClickable(true);
//                btnNext.setClickable(true);
//            }, 5000);
//        });
//
//        btnPre.setOnClickListener(view -> {
//            // Xử lý sự kiện cho nút btnPre
//            if (songList.size() > 0) {
//                if (mediaPlayer != null && (mediaPlayer.isPlaying() || mediaPlayer != null)) {
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                }
//                if (position > 0) {
//                    btnPlayPause.setImageResource(R.drawable.ic_pause);
//                    position--;
//                    if (repeat) {
//                        if (position == songList.size() - 1) {
//                            position = -1;
//                        }
//                        position += 1;
//                    }
//                    if (checkRandom) {
//                        Random random = new Random();
//                        int iRandom = random.nextInt(songList.size());
//                        if (iRandom == position) {
//                            position = iRandom + 1;
//                        }
//                        position = iRandom;
//                    }
//                    if (position < 0) {
//                        position = songList.size() - 1;
//                    }
//                    Glide.with(this)
//                            .load(songList.get(position).getImageSong())
//                            .into(imgSong);
//                    tvName.setText(songList.get(position).getNameSong());
//                    tvNameGroup.setText(songList.get(position).getNameGroup());
//                    btnPlayPause.setImageResource(R.drawable.ic_pause);
//                    playSong(songList.get(position).getLinkSong());
//                    updateTime();
//                }
//            }
//
//            btnPre.setClickable(false);
//            btnNext.setClickable(false);
//            Handler handler = new Handler();
//            handler.postDelayed(() -> {
//                btnPre.setClickable(true);
//                btnNext.setClickable(true);
//            }, 5000);
//        });
//    }
//
//    private List<Song> initializeSongList() {
//        List<Song> songs = new ArrayList<>();
//        // Thêm các bài hát vào danh sách
//        songs.add(new Song("Song 1", "Artist 1", "https://example.com/image1.jpg", "https://example.com/song1.mp3"));
//        songs.add(new Song("Song 2", "Artist 2", "https://example.com/image2.jpg", "https://example.com/song2.mp3"));
//        // Tiếp tục thêm các bài hát khác
//        return songs;
//    }
//
//    private void playSong(String linkSong) {
//        try {
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(linkSong);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            // Cập nhật giao diện khi bài hát bắt đầu chơi
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateTime() {
//        // Cập nhật thời gian bài hát
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    songProgress.setProgress(mediaPlayer.getCurrentPosition());
//                    handler.postDelayed(this, 1000);
//                }
//            }
//        }, 1000);
//    }
}

