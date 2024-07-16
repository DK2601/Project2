package com.project.spotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;

import java.util.List;

import kaaes.spotify.webapi.android.models.PlaylistSimple;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private List<PlaylistSimple> playlistSimpleList;
    private Context context;

    public PlaylistAdapter(List<PlaylistSimple> playlistSimpleList, Context context) {
        this.playlistSimpleList = playlistSimpleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistSimple playlistSimple = playlistSimpleList.get(position);
        holder.tvName.setText(playlistSimple.name);
        holder.tvArtist.setText(playlistSimple.owner.display_name);
    }

    @Override
    public int getItemCount() {
        return playlistSimpleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvArtist = itemView.findViewById(R.id.tvArtist);
        }
    }
}
