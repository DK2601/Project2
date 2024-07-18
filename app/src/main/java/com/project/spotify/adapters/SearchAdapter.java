package com.project.spotify.adapters;

import android.content.Context;
import android.drm.DrmStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.spotify.PlayBack;
import com.project.spotify.R;
import com.project.spotify.entity.Artist;
import com.project.spotify.entity.Album;
import com.project.spotify.entity.Playlist;
import com.project.spotify.entity.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Object> items;
    private Context context;
    private OnItemClickListener listener;
    private List<PlayBack> playBack;
    public interface OnItemClickListener {
        void onItemClick(PlayBack playBack);
    }

    public void setOnItemClickListener(SearchAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

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
            holder.nameTextView.setText(album.getName());
            holder.typeTextView.setText(album.getType());
            Picasso.get().load(album.getImageUrl()).into(holder.imageView);
        } else if (item instanceof Artist) {
            Artist artist = (Artist) item;
            holder.nameTextView.setText(artist.getName());
            holder.typeTextView.setText(artist.getType());
            Picasso.get().load(artist.getImageUrl()).into(holder.imageView);
        } else if (item instanceof Track) {
            Track track = (Track) item;
            holder.nameTextView.setText(track.getName());
            holder.typeTextView.setText(track.getType());
            Picasso.get().load(track.getImageUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView typeTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            imageView = itemView.findViewById(R.id.imageView);

        }

        public void bind(final PlayBack playBack, final SearchAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(playBack);
                    }
                }
            });
        }
    }
}
