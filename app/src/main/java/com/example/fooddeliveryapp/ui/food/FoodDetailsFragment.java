package com.example.fooddeliveryapp.ui.food;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentFoodDetailsBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;

public class FoodDetailsFragment extends Fragment {
    private FragmentFoodDetailsBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();

        imageList.add(new SlideModel("https://bit.ly/2YoJ77H", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/2BteuF2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/3fLJf72", "And people do that.", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList);

        binding.btnGoToCart2.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart));
        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        binding.btnAddToCart.setOnClickListener(v -> {
//            Implement add to cart
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart);
        });

        return root;
    }

}