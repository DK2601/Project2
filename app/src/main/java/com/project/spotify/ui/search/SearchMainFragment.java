package com.project.spotify.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.project.spotify.CategoryRequest;
import com.project.spotify.PlayBack;
import com.project.spotify.PlaylistActivity;
import com.project.spotify.SearchRequest;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.project.spotify.adapters.SearchAdapter;
import com.project.spotify.adapters.UserPlaylistAdapter;
import com.project.spotify.entity.Album;
import com.project.spotify.entity.Artist;
import com.project.spotify.entity.Playlist;
import com.project.spotify.entity.Track;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchMainFragment extends Fragment {
    private List<Object> items = new ArrayList<>();
    private SearchAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_main, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new SearchAdapter(items, requireContext());
        recyclerView.setAdapter(adapter);
        EditText searchEditText = view.findViewById(R.id.inputSearch);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                searchSpotify(query);
            }
        });

    }

    private void searchSpotify(String query) {
        SearchRequest searchRequest = new SearchRequest(requireContext());
        searchRequest.fetchSearch(query, new SearchRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("SearchFragment", "Search Response: " + response.toString());
                items.clear();
                try {
                    // Get arrays of different types
                    JSONArray playlistItems = response.getJSONObject("playlists").getJSONArray("items");
                    JSONArray artistItems = response.getJSONObject("artists").getJSONArray("items");
                    JSONArray trackItems = response.getJSONObject("tracks").getJSONArray("items");

                    // Add items according to the order in which they appear
                    items.addAll(parseItems(trackItems, "track"));
                    items.addAll(parseItems(playlistItems, "playlist"));
                    items.addAll(parseItems(artistItems, "artist"));
                } catch (JSONException e) {
                    Log.e("SearchFragment", "Error parsing JSON", e);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("SearchFragment", "Error fetching search results", error);
            }
        });
    }

    private List<Object> parseItems(JSONArray itemsArray, String itemType) throws JSONException {
        List<Object> items = new ArrayList<>();
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject item = itemsArray.getJSONObject(i);
            switch (itemType) {
                case "playlist":
                    items.add(parsePlaylist(item));
                    break;
                case "artist":
                    items.add(parseArtist(item));
                    break;
                case "track":
                    items.add(parseTrack(item));
                    break;
            }
        }
        return items;
    }

    private Album parsePlaylist(JSONObject item) throws JSONException {
        String name = item.getString("name");
        String type = item.getString("type");
        String imageUrl = item.getJSONArray("images").getJSONObject(0).getString("url");
        String artist = item.getJSONObject("owner").getString("display_name");
        return new Album(name, type, imageUrl, artist);
    }

    private Artist parseArtist(JSONObject item) throws JSONException {
        String name = item.getString("name");
        String type = item.getString("type");
        String imageUrl = item.getJSONArray("images").getJSONObject(0).getString("url");
        return new Artist(name, type, imageUrl);
    }

    private Track parseTrack(JSONObject item) throws JSONException {
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

        return new Track(name, type, artistName, imageUrl);
    }




}
