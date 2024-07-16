package com.project.spotify.ui.home;

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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.project.spotify.PlaylistRequest;
import com.project.spotify.R;
import com.project.spotify.adapters.CardViewAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private List<JSONObject> trackItems;
    private ImageView profileImageView;

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
        adapter = new CardViewAdapter(trackItems);
        recyclerView.setAdapter(adapter);
        profileImageView = view.findViewById(R.id.profile_image);

        // Hiển thị token
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_key), requireContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "No token found");

        // Lấy danh sách track
        PlaylistRequest playlistRequest = new PlaylistRequest(requireContext());
        playlistRequest.fetchPlaylists(new PlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("HomeFragment", "Response: " + response.toString());
                    JSONArray items = response.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        JSONObject track = item.getJSONObject("track");
                        trackItems.add(track);
                    }
                    adapter.notifyDataSetChanged();
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
    }
}
