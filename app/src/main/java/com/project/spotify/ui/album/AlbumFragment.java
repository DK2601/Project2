package com.project.spotify.ui.album;

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
import com.project.spotify.additional.Track;
import com.project.spotify.additional.TrackAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private List<Track> trackList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.track_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        trackList = new ArrayList<>();
        trackList.add(new Track("đưa em về nhà", "GREY D"));
        trackList.add(new Track("giữ lấy làm gì", "GREY D"));
        trackList.add(new Track("dự báo thời tiết hôm nay mưa", "GREY D"));

        trackAdapter = new TrackAdapter(trackList);
        recyclerView.setAdapter(trackAdapter);
    }
}