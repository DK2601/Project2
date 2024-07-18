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

public class CategoryRequest {

    private static final String CATEGORY_ENDPOINT = "https://api.spotify.com/v1/browse/categories";

    private RequestQueue requestQueue;

    private SharedPreferences sharedPreferences;

    public CategoryRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
    }

    public void fetchCategories(final VolleyCallback callback) {
        String token = sharedPreferences.getString("token", "");
        Log.d("CategoryRequest", "Token: " + token);
        Log.d("CategoryRequest", "URL: " + CATEGORY_ENDPOINT);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                CATEGORY_ENDPOINT,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CategoryRequest", "Response: " + response.toString());
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("CategoryRequest", "Error fetching categories", error);
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
