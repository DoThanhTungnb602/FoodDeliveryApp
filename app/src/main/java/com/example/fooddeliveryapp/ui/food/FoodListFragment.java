package com.example.fooddeliveryapp.ui.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.databinding.FragmentFoodListBinding;

import java.util.List;


public class FoodListFragment extends Fragment {
    private FragmentFoodListBinding binding;
    private RecyclerView recyclerView;
    private FoodListAdapter foodListAdapter;
    private final List<Food> foodList;

    public FoodListFragment(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFoodListBinding.inflate(inflater, container, false);
        MainActivity.hideNavView();

        recyclerView = binding.recyclerViewFoodList;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        foodListAdapter = new FoodListAdapter(foodList);
        recyclerView.setAdapter(foodListAdapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}