package com.example.fooddeliveryapp.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Favorite;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.FavoriteRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
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

    private FavoriteRepository favoriteRepository;
    private List<Favorite> favoriteList;
    private FoodRepository foodRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.showNavView();
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        favoriteRepository = new FavoriteRepository(AppDatabase.getDatabase(requireActivity()));
        foodRepository = new FoodRepository(AppDatabase.getDatabase(requireActivity()));
        // Get nav controller
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Get favorite food list from database
        // TODO: Get favorite food list from database
        favoriteFoodList = new ArrayList<>();
        favoriteList = favoriteRepository.getFavoriteList();
        // If there is no favorite food, show no favorite layout
        if (favoriteList.size() == 0) {
            binding.layoutNoFavorite.setVisibility(View.VISIBLE);
            binding.layoutExistFavorite.setVisibility(View.GONE);
        } else {
            for (int i=0;i< favoriteList.size();i++){
                Food food;
                food = foodRepository.getFoodById(favoriteList.get(i).foodId);
                favoriteFoodList.add(food);
            }
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