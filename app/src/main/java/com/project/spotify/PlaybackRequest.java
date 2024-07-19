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

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlaybackRequest {
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private static final String TRACK_ENDPOINT = "https://api.spotify.com/v1/tracks/4nKRZAONxGgcKCMin730Ai?market=VN";

    public PlaybackRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }

    public void fetchTrack(final VolleyCallback callback) {
        String token = sharedPreferences.getString("token", "");
        Log.d("PlaybackRequest", "Token: " + token );
        Log.d("PlaybackRequest", "URL: " + TRACK_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, TRACK_ENDPOINT, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaybackRequest", "Error fetching track", error);
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
    };
}
