package com.project.spotify.additional;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;

import java.util.ArrayList;
import java.util.List;

public class Album extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        recyclerView = findViewById(R.id.track_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize trackList and add some sample data
        trackList = new ArrayList<>();
        trackList.add(new Track("đưa em về nhà", "GREY D"));
        trackList.add(new Track("giữ lấy làm gì", "GREY D"));
        trackList.add(new Track("dự báo thời tiết hôm nay mưa", "GREY D"));

        trackAdapter = new TrackAdapter(trackList);
        recyclerView.setAdapter(trackAdapter);
    }
}
