package com.project.spotify.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.VolleyError;
import com.project.spotify.PlaylistRequest;
import com.project.spotify.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


public class UserFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private ImageView profileImageView;
    private TextView display_name;
    private TextView follower;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        profileImageView = view.findViewById(R.id.profile_image);
        follower = view.findViewById(R.id.follower);
        display_name = view.findViewById(R.id.display_name);
//
        // Hiển thị token
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.shared_pref_key), requireContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "No token found");
//
       PlaylistRequest userProfileRequest = new PlaylistRequest(requireContext());
//
        userProfileRequest.fetchUserProfile(new PlaylistRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    Log.d("HomeFragment", "User Response: " + response.toString());

                    // Lấy mảng images
                    JSONArray images = response.getJSONArray("images");
                    if (images.length() > 0) {
                        // Lấy URL của ảnh lớn hơn
                        String imageUrl = images.getJSONObject(1).getString("url");
                        Picasso.get().load(imageUrl).into(profileImageView);
                    }

                    // Lấy số lượng followers
                    JSONObject followersObject = response.getJSONObject("followers");
                    String totalFollowers = followersObject.getString("total"); // Không cần parseInt
                    follower.setText(totalFollowers + " followers");

                    // Lấy tên hiển thị
                    String displayName = response.getString("display_name");
                    display_name.setText(displayName);

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
