package com.project.spotify.ui.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.project.spotify.additional.Song;
import com.project.spotify.additional.SongAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArtistFragment extends Fragment {

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Song> songList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_body);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        songList = new ArrayList<>();
        songList.add(new Song("đưa em về nhà", "25.152.034"));
        songList.add(new Song("giữ lấy làm gì", "15.199.147"));
        songList.add(new Song("dự báo thời tiết hôm nay mưa", "30.180.985"));

        songAdapter = new SongAdapter(songList);
        recyclerView.setAdapter(songAdapter);
    }
}