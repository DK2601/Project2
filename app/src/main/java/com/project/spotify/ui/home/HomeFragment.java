package com.project.spotify.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.project.spotify.PlaylistActivity;
import com.project.spotify.PlaylistRequest;
import com.project.spotify.R;
import com.project.spotify.adapters.ArtistAdapter;
import com.project.spotify.adapters.CardViewAdapter;
import com.project.spotify.adapters.UserPlaylistAdapter;
import com.project.spotify.entity.Playlist;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView, recyclerViewHorizontal;
    private CardViewAdapter adapter, horizontalAdapter;
    private List<JSONObject> trackItems, horizontalTrackItems;
    private Set<String> trackIds, horizontalTrackIds; // Sets to track IDs to avoid duplicates
    private ImageView profileImageView;
    private RecyclerView recyclerViewArtist;
    private ArtistAdapter artistAdapter;
    private List<JSONObject> artistItems;
    private Set<String> artistIds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns
        trackItems = new ArrayList<>();
        trackIds = new HashSet<>();
        adapter = new CardViewAdapter(trackItems);
        recyclerView.setAdapter(adapter);

        recyclerViewHorizontal = view.findViewById(R.id.recyclerViewHorizontal);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        horizontalTrackItems = new ArrayList<>();
        horizontalTrackIds = new HashSet<>();
        horizontalAdapter = new CardViewAdapter(horizontalTrackItems);
        recyclerViewHorizontal.setAdapter(horizontalAdapter);

        profileImageView = view.findViewById(R.id.profile_image);


        recyclerViewArtist = view.findViewById(R.id.recyclerViewArtist);
        recyclerViewArtist.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        artistItems = new ArrayList<>();
        artistIds = new HashSet<>();
        artistAdapter = new ArtistAdapter(artistItems);
        recyclerViewArtist.setAdapter(artistAdapter);

        // Hiển thị token
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_key), requireContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "No token found");

        // Lấy danh sách track
        PlaylistRequest playlistRequest = new PlaylistRequest(requireContext());
        playlistRequest.fetchPlaylists(new PlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("HomeFragment", "Playlist Response: " + response.toString());
                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        JSONObject album = item.getJSONObject("album");
                        String albumId = album.getString("id");

                        // Kiểm tra và thêm vào danh sách cho RecyclerView chính
                        if (!trackIds.contains(albumId)) {
                            trackItems.add(album); // Add album object for main list
                            trackIds.add(albumId);
                        }

                        // Kiểm tra và thêm vào danh sách cho RecyclerView ngang
                        if (!horizontalTrackIds.contains(albumId)) {
                            horizontalTrackItems.add(album); // Add album object for horizontal list
                            horizontalTrackIds.add(albumId);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    horizontalAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HomeFragment", "Error parsing JSON", e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("HomeFragment", "Error fetching playlists", error);
            }
        });

        // Lấy thông tin người dùng
        playlistRequest.fetchUserProfile(new PlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("HomeFragment", "User Response: " + response.toString());
                    JSONArray images = response.getJSONArray("images");
                    if (images.length() > 0) {
                        String imageUrl = images.getJSONObject(0).getString("url");
                        Picasso.get().load(imageUrl).into(profileImageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HomeFragment", "Error parsing user JSON", e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("HomeFragment", "Error fetching user profile", error);
            }
        });

        playlistRequest.fetchArtist(new PlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("HomeFragment", "Artist Response: " + response.toString());
                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String artistId = item.getString("id");

                        // Check and add to the list for the artist RecyclerView
                        if (!artistIds.contains(artistId)) {
                            artistItems.add(item); // Add artist object for artist list
                            artistIds.add(artistId);
                        }
                    }
                    artistAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("HomeFragment", "Error parsing artist JSON", e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("HomeFragment", "Error fetching artist", error);
            }
        });

    }
}
