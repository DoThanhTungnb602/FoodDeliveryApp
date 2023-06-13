package com.example.fooddeliveryapp.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.databinding.FragmentFavoriteBinding;
import com.example.fooddeliveryapp.ui.food.FoodListAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding binding;
    private RecyclerView recyclerView;
    private FoodListAdapter favoriteListAdapter;
    private List<Food> favoriteFoodList;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        // Get nav controller
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Get favorite food list from database
        // TODO: Get favorite food list from database
        favoriteFoodList = new ArrayList<>();

        // If there is no favorite food, show no favorite layout
        if (favoriteFoodList.size() == 0) {
            binding.layoutNoFavorite.setVisibility(View.VISIBLE);
            binding.layoutExistFavorite.setVisibility(View.GONE);
        } else {
            binding.layoutNoFavorite.setVisibility(View.GONE);
            binding.layoutExistFavorite.setVisibility(View.VISIBLE);
            recyclerView = binding.recyclerViewFavorite;
            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
            favoriteListAdapter = new FoodListAdapter(favoriteFoodList);
            recyclerView.setAdapter(favoriteListAdapter);
        }

        binding.btnGoToCart.setOnClickListener(v -> {
            MainActivity.navigateTo(requireActivity(), R.id.navigation_cart);
        });

        return binding.getRoot();
    }
}