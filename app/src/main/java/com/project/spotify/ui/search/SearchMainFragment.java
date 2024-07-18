package com.project.spotify.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.project.spotify.CategoryRequest;
import com.project.spotify.SearchRequest;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.project.spotify.adapters.SearchAdapter;
import com.project.spotify.entity.Album;
import com.project.spotify.entity.Artist;
import com.project.spotify.entity.Track;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchMainFragment extends Fragment {
    private List<Object> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_main, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        SearchRequest searchRequest = new SearchRequest(requireContext());

        searchRequest.fetchSearch(new SearchRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("SearchFragment", "Search Response: " + response.toString());
                try {
                    items.addAll(parsePlaylists(response.getJSONObject("playlists").getJSONArray("items")));
                    items.addAll(parseArtists(response.getJSONObject("artists").getJSONArray("items")));
                    items.addAll(parseTracks(response.getJSONObject("tracks").getJSONArray("items")));
                } catch (JSONException e) {
                    Log.e("SearchFragment", "Error parsing JSON", e);
                }

                SearchAdapter adapter = new SearchAdapter(items, requireContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("SearchFragment", "Error fetching search results", error);
            }
        });
    }

    private List<Album> parsePlaylists(JSONArray items) throws JSONException {
        List<Album> albums = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String name = item.getString("name");
            String type = item.getString("type");
            String imageUrl = item.getJSONArray("images").getJSONObject(0).getString("url");
            String artist = item.getJSONObject("owner").getString("display_name");
            albums.add(new Album(name, type, imageUrl, artist));
        }
        return albums;
    }

    private List<Artist> parseArtists(JSONArray items) throws JSONException {
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String name = item.getString("name");
            String type = item.getString("type");
            String imageUrl = item.getJSONArray("images").getJSONObject(0).getString("url");
            artists.add(new Artist(name, type, imageUrl));
        }
        return artists;
    }

    private List<Track> parseTracks(JSONArray items) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String name = item.getString("name");
            String type = item.getString("type");
            String artistName = item.getJSONArray("artists").getJSONObject(0).getString("name");
            String imageUrl = null;

            // Lấy imageUrl từ album
            JSONObject album = item.getJSONObject("album");
            JSONArray imagesArray = album.getJSONArray("images");
            if (imagesArray.length() > 0) {
                imageUrl = imagesArray.getJSONObject(0).getString("url");
            }

            tracks.add(new Track(name, type, artistName, imageUrl));
        }
        return tracks;
    }


}
