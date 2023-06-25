package com.example.fooddeliveryapp.ui.search;

import android.annotation.SuppressLint;
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
import com.example.fooddeliveryapp.data.db.dao.FoodDao;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.FragmentSearchBinding;
import com.example.fooddeliveryapp.ui.food.FoodListAdapter;

import java.util.List;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private RecyclerView recyclerView;
    private FoodListAdapter searchListAdapter;
    private List<Food> searchedFoodList;
    private FoodRepository foodRepository;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        foodRepository = new FoodRepository(AppDatabase.getDatabase(requireActivity()));
        assert getArguments() != null;
        searchedFoodList = foodRepository.searchFood(getArguments().getString("name"));
        recyclerView = binding.recylerViewSearchList;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        searchListAdapter = new FoodListAdapter(searchedFoodList);
        recyclerView.setAdapter(searchListAdapter);
        binding.NumberOfResult.setText("Tìm được " + String.valueOf(searchedFoodList.size()) + " kết quả");

        if (searchListAdapter.getItemCount() == 0) {
            binding.layoutItemFound.setVisibility(View.GONE);
            binding.layoutNoItemFound.setVisibility(View.VISIBLE);
        } else {
            binding.layoutItemFound.setVisibility(View.VISIBLE);
            binding.layoutNoItemFound.setVisibility(View.GONE);
        }

        binding.btnBack.setOnClickListener(v -> {
            navController.navigate(R.id.action_searchFragment_to_navigation_home);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}