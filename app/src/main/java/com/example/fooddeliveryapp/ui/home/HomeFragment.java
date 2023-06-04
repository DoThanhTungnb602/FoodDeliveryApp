package com.example.fooddeliveryapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private FoodListAdapter foodListAdapter;
    private List<Food> listFood;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        recyclerView = binding.recylerViewFoodList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listFood = new ArrayList<>();

        foodListAdapter = new FoodListAdapter(listFood);
        recyclerView.setAdapter(foodListAdapter);

        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_cart);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}