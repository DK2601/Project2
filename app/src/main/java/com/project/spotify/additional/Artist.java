package com.project.spotify.additional;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;

import java.util.ArrayList;
import java.util.List;

public class Artist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        recyclerView = findViewById(R.id.recycler_view_body);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songList = new ArrayList<>();
        songList.add(new Song("đưa em về nhà", "25.152.034"));
        songList.add(new Song("giữ lấy làm gì", "15.199.147"));
        songList.add(new Song("dự báo thời tiết hôm nay mưa", "30.180.985"));

        songAdapter = new SongAdapter(songList);
        recyclerView.setAdapter(songAdapter);
    }
}
