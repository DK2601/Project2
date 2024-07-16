package com.project.spotify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.adapters.PlaylistAdapter;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.Track;
import retrofit.client.Response;

public class PlaylistActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    PlaylistAdapter playlistAdapter;
    RecyclerView rvPlayList;
    List<PlaylistSimple> playlistSimpleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref_key),
                MODE_PRIVATE);
        init();


        SpotifyApi api = new SpotifyApi();
        api.setAccessToken(Common.TOKKEN);
        SpotifyService spotify = api.getService();

        spotify.getMyPlaylists(new SpotifyCallback<Pager<PlaylistSimple>>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Toast.makeText(getApplicationContext(), "Could not load playlist", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(Pager<PlaylistSimple> playlistSimplePager, Response response) {
                playlistSimpleList.clear();
                for (PlaylistSimple playlistSimple : playlistSimplePager.items) {
                    playlistSimpleList.add(playlistSimple);
                }
                playlistAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        playlistAdapter = new PlaylistAdapter(playlistSimpleList, PlaylistActivity.this);
        rvPlayList = findViewById(R.id.rvPlayList);
        rvPlayList.setAdapter(playlistAdapter);
    }
}