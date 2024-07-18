package com.project.spotify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.project.spotify.entity.Playlist;
import com.project.spotify.entity.UserInfo;

import java.util.List;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder> {

    private List<UserInfo> userInfos;

    public UserProfileAdapter(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }


    @NonNull
    @Override
    public UserProfileAdapter.UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_main_list_item, parent, false);
        return new UserProfileAdapter.UserProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class UserProfileViewHolder extends RecyclerView.ViewHolder {
        public UserProfileViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
