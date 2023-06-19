package com.example.fooddeliveryapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodDao;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding;
import com.example.fooddeliveryapp.ui.category.CategoryListAdapter;
import com.example.fooddeliveryapp.ui.food.FoodListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewFoodList;
    private FoodListAdapter foodListAdapter;
    private List<Food> listFood;
    private RecyclerView recyclerViewCategory;
    private CategoryListAdapter categoryListAdapter;
    private List<Category> listCategory;
    private AppDatabase database;
    private FoodRepository foodRepository;
    private FoodDao foodDao;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        database = AppDatabase.getDatabase(getContext());
        foodRepository = new FoodRepository(database);

        MainActivity.showNavView();

        recyclerViewFoodList = binding.recyclerViewFoodList;
        recyclerViewFoodList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        listFood = new ArrayList<>();
        listFood = foodRepository.getAllFood();
        // TODO: Add data to listFood


        listCategory = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            listCategory.add(null);
        }

//        Toast.makeText(getContext(), listFood.get(0).getFoodImages().size(), Toast.LENGTH_SHORT).show();
        foodListAdapter = new FoodListAdapter(listFood);
        recyclerViewFoodList.setAdapter(foodListAdapter);

        categoryListAdapter = new CategoryListAdapter(listCategory);
        recyclerViewCategory = binding.recyclerViewCategoryHome;
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewCategory.setAdapter(categoryListAdapter);

        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_cart);
            MainActivity.hideNavView();
        });

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
        binding.editTextSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_searchFragment);
                MainActivity.hideNavView();
                return true;
            }
            return false;
        });

        binding.btnSeeAllCategory.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_categoryFragment);
            MainActivity.hideNavView();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void search(String text){
        this.foodDao = database.foodDao();
        foodDao.searchFood("%" + text + "%");
    }
}