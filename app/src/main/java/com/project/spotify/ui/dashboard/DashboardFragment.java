package com.project.spotify.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.project.spotify.CategoryRequest;
import com.project.spotify.R;
import com.project.spotify.adapters.CategoryAdapter;
import com.project.spotify.entity.Category;
import com.project.spotify.ui.search.SearchMainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText searchField = view.findViewById(R.id.editTextSearch);
        searchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang SearchMainFragment khi click vào EditText
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup) getView().getParent()).getId(), new SearchMainFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewArtist);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Set layout manager here

        CategoryRequest categoryRequest = new CategoryRequest(requireContext());
        categoryRequest.fetchCategories(new CategoryRequest.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("DashboardFragment", "Category Response: " + response.toString());
                try {
                    JSONObject categories = response.getJSONObject("categories");
                    JSONArray items = categories.getJSONArray("items");
                    List<Category> categoryList = new ArrayList<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String name = item.getString("name");
                        JSONArray icons = item.getJSONArray("icons");
                        String iconUrl = icons.getJSONObject(0).getString("url");
                        categoryList.add(new Category(name, iconUrl));
                    }
                    // Set the adapter after populating the categoryList
                    CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, requireContext());
                    recyclerView.setAdapter(categoryAdapter);
                } catch (JSONException e) {
                    Log.e("DashboardFragment", "Error parsing JSON", e);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("DashboardFragment", "Error fetching categories", error);
            }
        });
    }
}
