package com.example.fooddeliveryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.ActivityMainBinding;
import com.example.fooddeliveryapp.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;
    private ActivityMainBinding binding;
    private static BottomNavigationView navView;
    private int currentDestinationId;
    FoodRepository foodRepository;
    private boolean doubleBackToExitPressedOnce = false;
    public static final int currentUserID = 1;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(this);
        foodRepository = new FoodRepository(database);

        currentDestinationId = R.id.navigation_home;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        navView = binding.navView;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            currentDestinationId = destination.getId();
        });

        FakeData fakeData = new FakeData();
//        fakeData.resetData(this);
        fakeData.fetchDataFromServerToDatabase(this);
    }


    public static void hideNavView() {
        navView.setVisibility(View.GONE);
    }

    public static void showNavView() {
        navView.setVisibility(View.VISIBLE);
    }

    public static void navigateTo(Context context, int id) {
        Navigation.findNavController((AppCompatActivity) context, R.id.nav_host_fragment_activity_main).navigate(id);
    }

    @Override
    public void onBackPressed() {
        if (HomeFragment.isSearching) {
            HomeFragment.closeSearch();
            return;
        }
        if (currentDestinationId == R.id.navigation_home) {
            if (doubleBackToExitPressedOnce) {
                this.finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        } else if (currentDestinationId == R.id.navigation_history || currentDestinationId == R.id.navigation_user) {
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_home);

        } else if (currentDestinationId == R.id.navigation_auth) {
            this.finish();
            return;
        } else {
            super.onBackPressed();

        }
    }
}