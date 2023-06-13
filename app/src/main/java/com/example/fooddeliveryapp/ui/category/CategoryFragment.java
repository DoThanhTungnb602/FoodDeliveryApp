package com.example.fooddeliveryapp.ui.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<Category> categoryList;
    private CategoryListAdapter categoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        categoryList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            categoryList.add(null);
        }

        recyclerView = binding.recyclerViewCategoryList;
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryListAdapter(categoryList);
        recyclerView.setAdapter(categoryAdapter);

        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        return binding.getRoot();
    }
}