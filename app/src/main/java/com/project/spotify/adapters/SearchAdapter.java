package com.project.spotify.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.project.spotify.entity.Artist;
import com.project.spotify.entity.Album;
import com.project.spotify.entity.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Object> items; // This list will contain Album, Artist, and Track objects
    private Context context;

    public SearchAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_main_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);
        if (item instanceof Album) {
            Album album = (Album) item;
            Log.d("SearchAdapter", "Album name: " + album.getName()); // Add this line
            holder.textView.setText(album.getName() + " - " + album.getArtist());
            Picasso.get().load(album.getImageUrl()).into(holder.imageView);
        } else if (item instanceof Artist) {
            Artist artist = (Artist) item;
            Log.d("SearchAdapter", "Artist name: " + artist.getName()); // Add this line
            holder.textView.setText(artist.getName());
            Picasso.get().load(artist.getImageUrl()).into(holder.imageView);
        } else if (item instanceof Track) {
            Track track = (Track) item;
            Log.d("SearchAdapter", "Track name: " + track.getName()); // Add this line
            holder.textView.setText(track.getName() + " - " + track.getArtist());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
