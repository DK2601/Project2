package com.project.spotify.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.project.spotify.PlaylistActivity;
import com.project.spotify.PlaylistRequest;
import com.project.spotify.R;
import com.project.spotify.UserPlaylistRequest;
import com.project.spotify.adapters.UserPlaylistAdapter;
import com.project.spotify.databinding.FragmentNotificationsBinding;
import com.project.spotify.entity.Playlist;
import com.project.spotify.ui.search.SearchMainFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;
    private UserPlaylistAdapter playlistAdapter;
    private List<Playlist> playlistList = new ArrayList<>();
    private ImageView profileImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewHorizontal);
        playlistAdapter = new UserPlaylistAdapter(playlistList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(playlistAdapter);
        profileImageView = view.findViewById(R.id.profile_image);


        UserPlaylistRequest userPlaylistRequest = new UserPlaylistRequest(requireContext());


        playlistAdapter.setOnItemClickListener(new UserPlaylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Playlist playlist) {
                Intent intent = new Intent(getActivity(), PlaylistActivity.class);
                // Nếu bạn cần chuyển dữ liệu sang Activity
                // intent.putExtra("KEY_NAME", "value");
                startActivity(intent);
            }
        });
        userPlaylistRequest.fetchUserPlaylist(new UserPlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("User Playlist", "Response: " + response.toString());
                parsePlaylists(response);
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("User Playlist", "Response: " + error.toString());
            }
        });

        userPlaylistRequest.fetchUserProfile(new PlaylistRequest.VolleyCallback() {
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

    private void parsePlaylists(JSONObject response) {
        try {
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String name = item.getString("name");
                String type = item.getString("type");
                String imageUrl = item.getJSONArray("images").getJSONObject(0).getString("url");

                Playlist playlist = new Playlist(name, type, imageUrl);
                playlistList.add(playlist);
            }

            playlistAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
