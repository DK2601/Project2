package com.project.spotify;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlaylistRequest {

    private static final String PLAYLIST_ENDPOINT = "https://api.spotify.com/v1/me/top/tracks?limit=6";
    private static final String ARTIST_ENDPOINT = "https://api.spotify.com/v1/me/top/artists";
    private static final String USER_PROFILE_ENDPOINT = "https://api.spotify.com/v1/me";
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    public PlaylistRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
    }

    public void fetchPlaylists(final VolleyCallback callback) {
        String token = sharedPreferences.getString("token", "");
        Log.d("PlaylistRequest", "Token: " + token);
        Log.d("PlaylistRequest", "URL: " + PLAYLIST_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                PLAYLIST_ENDPOINT,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaylistRequest", "Error fetching playlists", error);
                        callback.onError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void fetchUserProfile(final VolleyCallback callback) {
        String token = sharedPreferences.getString("token", "");
        Log.d("PlaylistRequest", "Token: " + token);
        Log.d("PlaylistRequest", "URL: " + USER_PROFILE_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                USER_PROFILE_ENDPOINT,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaylistRequest", "Error fetching user profile", error);
                        callback.onError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void fetchArtist(final VolleyCallback callback) {
        String token = sharedPreferences.getString("token", "");
        Log.d("PlaylistRequest", "Token: " + token);
        Log.d("PlaylistRequest", "URL: " + ARTIST_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                ARTIST_ENDPOINT,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaylistRequest", "Error fetching artists", error);
                        callback.onError(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }
}
