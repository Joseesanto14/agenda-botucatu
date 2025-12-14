package com.example.agendabotucatu.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agendabotucatu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth; // Import FirebaseAuth
import com.google.firebase.auth.FirebaseUser; // Import FirebaseUser

public class FeedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already in FeedActivity, no need to restart
                return true;
            } else if (itemId == R.id.nav_favoritos) {
                startActivity(new Intent(getApplicationContext(), FavoritosActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else if (itemId == R.id.nav_calendario) {
                startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));
                overridePendingTransition(0,0);
                finish();
                return true;
            } else if (itemId == R.id.nav_perfil) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {

                    Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                } else {
                    // Handle case where user is not logged in, e.g., redirect to LoginActivity
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                return true;
            }
            return false;
        });
    }
}