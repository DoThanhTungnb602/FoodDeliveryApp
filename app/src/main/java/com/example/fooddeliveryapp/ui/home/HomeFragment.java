package com.example.fooddeliveryapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.CategoryRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding;
import com.example.fooddeliveryapp.ui.category.CategoryListAdapter;
import com.example.fooddeliveryapp.ui.food.FoodListAdapter;
import com.example.fooddeliveryapp.ui.search.SearchListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static FragmentHomeBinding binding;
    private RecyclerView recyclerViewFoodList;
    private FoodListAdapter foodListAdapter;
    private List<Food> listFood;
    private RecyclerView recyclerViewCategory;
    private CategoryListAdapter categoryListAdapter;
    private List<Category> listCategory;
    private AppDatabase database;
    private FoodRepository foodRepository;
    private CategoryRepository categoryRepository;
    private RecyclerView recyclerViewSearch;
    private SearchListAdapter adapterSearch;
    private String key_search;
    private List<Food> listFoodSearch;
    public static boolean isSearching = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        database = AppDatabase.getDatabase(getContext());
        foodRepository = new FoodRepository(database);
        categoryRepository = new CategoryRepository(database);

        MainActivity.showNavView();

        binding.modal.setVisibility(View.GONE);

        listFood = foodRepository.getListFoodWithLimit(10);
        foodListAdapter = new FoodListAdapter(listFood);
        recyclerViewFoodList = binding.recyclerViewFoodList;
        recyclerViewFoodList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFoodList.setAdapter(foodListAdapter);

        listCategory = categoryRepository.getListCategoryWithLimit(6);
        categoryListAdapter = new CategoryListAdapter(listCategory);
        recyclerViewCategory = binding.recyclerViewCategoryHome;
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewCategory.setAdapter(categoryListAdapter);

        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_cart);
            MainActivity.hideNavView();
        });

        binding.editTextSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                HomeFragment.openSearch();
                if (!binding.editTextSearch.getText().toString().isEmpty()) {
                    binding.recyclerViewSearch.setVisibility(View.VISIBLE);
                } else {
                    binding.recyclerViewSearch.setVisibility(View.GONE);
                }
            }
        });
        binding.editTextSearch.setOnClickListener(v -> {
            HomeFragment.openSearch();
            if (!binding.editTextSearch.getText().toString().isEmpty()) {
                binding.recyclerViewSearch.setVisibility(View.VISIBLE);
            } else {
                binding.recyclerViewSearch.setVisibility(View.GONE);
            }
        });
        binding.modalOutside.setOnClickListener(v -> {
            HomeFragment.closeSearch();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getActivity().getCurrentFocus();
            if (view == null) {
                view = new View(getActivity());
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });


        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                key_search = String.valueOf(s);
                listFoodSearch = foodRepository.searchFood("%" + key_search + "%");
                if (key_search.isEmpty()) {
                    listFoodSearch = new ArrayList<>();
                    binding.recyclerViewSearch.setVisibility(View.GONE);
                } else {
                    binding.recyclerViewSearch.setVisibility(View.VISIBLE);
                    adapterSearch = new SearchListAdapter(listFoodSearch);
                    recyclerViewSearch = binding.recyclerViewSearch;
                    recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerViewSearch.setAdapter(adapterSearch);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.editTextSearch.setOnKeyListener((v, keyCode, event) -> {
            Bundle args = new Bundle();
            args.putString("name", "%" + key_search + "%");
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_searchFragment, args);
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

    public static void openSearch() {
        binding.modal.setVisibility(View.VISIBLE);
        enableDisableViewGroup(binding.content, false);
        isSearching = true;
    }

    public static void closeSearch() {
        binding.modal.setVisibility(View.GONE);
        enableDisableViewGroup(binding.content, true);
        isSearching = false;
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }
}