package com.example.fooddeliveryapp.ui.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.FragmentFoodBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class FoodFragment extends Fragment {
    AppDatabase database;
    private FoodViewPagerAdapter foodViewPagerAdapter;
    private List<Food> recommendedFoodList;
    private List<Food> bestSellerFoodList;
    private List<Food> highRatingFoodList;

    private GridLayoutManager gridLayoutManager;

    private RecyclerView recyclerView;
    private FragmentFoodBinding binding;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private FoodRepository foodRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        database = AppDatabase.getDatabase(requireActivity());

        foodRepository = new FoodRepository(database);
        assert getArguments() != null;

        List<Food> foodList = foodRepository.getFoodByCategoryName(getArguments().getString("categoryName"));
        recommendedFoodList = foodList;
        bestSellerFoodList = foodList;
        highRatingFoodList = foodList;

        foodViewPagerAdapter = new FoodViewPagerAdapter(getChildFragmentManager(), getLifecycle(), recommendedFoodList, bestSellerFoodList, highRatingFoodList);
        binding.viewPagerFoodList.setAdapter(foodViewPagerAdapter);

        new TabLayoutMediator(binding.tabLayoutFoodList, binding.viewPagerFoodList, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Gợi ý");
                    break;
                case 1:
                    tab.setText("Bán chạy");
                    break;
                case 2:
                    tab.setText("Đánh giá");
                    break;
            }
        }).attach();

        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        return binding.getRoot();
    }

}