package com.example.agendabotucatu.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.fragments.CalendarioFragment;
import com.example.agendabotucatu.fragments.FavoritosFragment;
import com.example.agendabotucatu.fragments.FeedFragment;
import com.example.agendabotucatu.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigation = findViewById(R.id.bottomNavigation);

        replaceFragment(new FeedFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new FeedFragment());
            } else if (itemId == R.id.nav_favoritos) {
                replaceFragment(new FavoritosFragment());
            } else if (itemId == R.id.nav_calendario) {
                replaceFragment(new CalendarioFragment());
            } else if (itemId == R.id.nav_perfil) {
                replaceFragment(new PerfilFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit();
    }
}