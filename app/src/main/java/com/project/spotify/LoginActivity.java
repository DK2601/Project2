package com.project.spotify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    // Volley request queue for API calls
    private RequestQueue requestQueue;

    // Request code that we'll use to judge if the result of
    // our call comes from the correct activity
    private static final int REQUEST_CODE = 1234;

    // The scopes needed for our app
    private static final String SCOPES
            = "user-read-email,user-read-private,playlist-modify-private,playlist-modify-public,playlist-read-private,playlist-read-collaborative,user-library-read,user-library-modify,user-read-playback-state,user-modify-playback-state,user-read-currently-playing,user-read-recently-played,user-top-read,user-follow-read,user-follow-modify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PlaylistActivity.class));
            }
        });
        sharedPreferences = this.getSharedPreferences(
                getString(R.string.shared_pref_key),
                MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

// Thay đổi quyền trong AuthorizationRequest.Builder
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(
                getString(R.string.CLIENT_ID),
                AuthorizationResponse.Type.TOKEN,
                getString(R.string.REDIRECT_URI)
        );
        builder.setScopes(new String[]{"user-read-email", "user-read-private", "playlist-read-private", "playlist-read-collaborative", "user-library-read", "user-library-modify", "user-read-playback-state", "user-modify-playback-state", "user-read-currently-playing", "user-read-recently-played", "user-top-read", "user-follow-read", "user-follow-modify"});
        AuthorizationRequest request = builder.build();


        // Opens the Login Activity Web page for spotify for auth
        AuthorizationClient.openLoginActivity(
                this, REQUEST_CODE, request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                case TOKEN:
                    editor = getSharedPreferences(getString(R.string.shared_pref_key), MODE_PRIVATE).edit();
                    Common.TOKKEN = response.getAccessToken();
                    editor.putString("token", response.getAccessToken());
                    editor.apply();

                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, BottomActivity.class);
                    intent.putExtra("initial_fragment", R.id.navigation_home);
                    startActivity(intent);
                    finish();
                    break;

                case ERROR:
                    Log.d("LoginActivity", "Error " + response.getError());
                    break;

                default:
                    Log.d("LoginActivity", "Error 2 " + response.getError());
            }
        }
    }



}