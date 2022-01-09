package com.lightlance.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.lightlance.app.fragment.AccountFragment;
import com.lightlance.app.fragment.CategoryFragment;
import com.lightlance.app.fragment.DashboardFragment;
import com.lightlance.app.fragment.StatusFragment;

import org.jetbrains.annotations.NotNull;

public class MainMenuActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bottomNav = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case  R.id.dashboardNav:
                        selectedFragment = new DashboardFragment();
                        break;
                    case R.id.categoryNav:
                        selectedFragment = new CategoryFragment();
                        break;
                    case R.id.statusNav:
                        selectedFragment = new StatusFragment();
                        break;
                    case R.id.accountNav:
                        selectedFragment = new AccountFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }
}