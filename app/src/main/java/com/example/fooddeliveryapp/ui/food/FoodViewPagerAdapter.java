package com.example.fooddeliveryapp.ui.food;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fooddeliveryapp.data.db.entities.Food;

import java.util.List;

public class FoodViewPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;
    List<Food> recommendedFoodList;
    List<Food> bestSellerFoodList;
    List<Food> highRatingFoodList;

    public FoodViewPagerAdapter(@NonNull FragmentManager fragment, Lifecycle lifecycle, List<Food> recommendedFoodList, List<Food> bestSellerFoodList, List<Food> highRatingFoodList) {
        super(fragment, lifecycle);
        this.recommendedFoodList = recommendedFoodList;
        this.bestSellerFoodList = bestSellerFoodList;
        this.highRatingFoodList = highRatingFoodList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new FoodListFragment(recommendedFoodList);
            case 1:
                return new FoodListFragment(bestSellerFoodList);
            case 2:
                return new FoodListFragment(highRatingFoodList);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
