package com.project.spotify.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private List<JSONObject> artists;

    public ArtistAdapter(List<JSONObject> artists) {
        this.artists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject artist = artists.get(position);
            String artistName = artist.getString("name");
            // Assuming the artist JSONObject has a field "imageUrl" with the URL of the artist's image
            JSONArray images = artist.getJSONArray("images");
            String imageUrl = images.getJSONObject(0).getString("url");

            holder.textView.setText(artistName);

            // Use Picasso to load the image from the URL into the ImageView
            Picasso.get().load(imageUrl).into(holder.imageView);

            // Set the artist data to the views in your layout
            // For example:
            // holder.textView.setText(artist.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define the views in your layout
        ImageView imageView;
        TextView textView;
        // TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Initialize the views in your layout
            imageView = view.findViewById(R.id.imageView); // Replace with your ImageView's id
            textView = view.findViewById(R.id.artist_name);
        }
    }
}