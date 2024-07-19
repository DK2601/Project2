package com.project.spotify.additional;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<Track> trackList;

    public TrackAdapter(List<Track> trackList) {
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_item, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = trackList.get(position);
        holder.trackName.setText(track.getTrackName());
        holder.artistName.setText(track.getArtistName());
        // Here you can set the image for imageView10 if necessary
        // holder.threeDots.setImageResource(R.drawable.three_dots);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {

        TextView trackName;
        TextView artistName;
        ImageView threeDots;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            trackName = itemView.findViewById(R.id.track_name);
            artistName = itemView.findViewById(R.id.artists_name);
            threeDots = itemView.findViewById(R.id.imageView10);
        }
    }
}

