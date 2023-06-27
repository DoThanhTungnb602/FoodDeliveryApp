package com.example.fooddeliveryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.ActivityMainBinding;
import com.example.fooddeliveryapp.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;
    private ActivityMainBinding binding;
    private static BottomNavigationView navView;
    private int currentDestinationId;
    FoodRepository foodRepository;
    UserRepository userRepository;
    private boolean doubleBackToExitPressedOnce = false;
    public static int currentUserID = 1;
    NavHostFragment navHostFragment;
    NavController navController;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(this);
        foodRepository = new FoodRepository(database);
        userRepository = new UserRepository(database);
        foodRepository.fetchDataFromServerToDatabase(this);
        currentDestinationId = R.id.navigation_home;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        navView = binding.navView;
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            currentDestinationId = destination.getId();
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentUserID = sharedPreferences.getInt("loggedUserID", 0);
        if (currentUserID == 0) {
            navController.navigate(R.id.navigation_auth);
        }
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
        if (HomeFragment.isSearching && currentDestinationId == R.id.navigation_home) {
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
        } else {
            super.onBackPressed();

        }
    }
}