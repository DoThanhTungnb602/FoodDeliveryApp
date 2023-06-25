package com.example.fooddeliveryapp.ui.cart;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.CartRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;
    private LiveData<List<Cart>> listCart;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private AppDatabase database;
    private FoodRepository foodRepository;
    private CartRepository cartRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerViewCartList;
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        database = AppDatabase.getDatabase(requireActivity());

        // thêm các list
        cartRepository = new CartRepository(database);
        foodRepository = new FoodRepository(database);

        // observe

        listCart = cartRepository.getAllCart();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartListAdapter = new CartListAdapter(listCart.getValue(), this);
        recyclerView.setAdapter(cartListAdapter);
        final Observer<List<Cart>> observer = cartListAdapter::setListCart;
        listCart.observe(this.getActivity(), observer);

        if (cartListAdapter.getItemCount() == 0) {
            binding.recyclerViewCartList.setVisibility(View.GONE);
            binding.layoutNoCart.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewCartList.setVisibility(View.VISIBLE);
            binding.layoutNoCart.setVisibility(View.GONE);
            binding.btnStartOrder.setText("Tiến hành đặt hàng");
        }

        binding.btnBack.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnStartOrder.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_cart_to_checkoutFragment);
        });

        updatePrice();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void updatePrice(){
        int Sum = 0;
        int quantity = 0;
        int price = 0;
//        for(Cart cart : listCart.getValue()){
//            quantity = cart.getQuantity();
//            price = foodRepository.getFoodById(cart.getFoodId()).price;
//            Sum += quantity*price;
//            System.out.println("Quantity : " + quantity + "\nPrice: " + price + "\nSum: " + Sum);
//        }
//        binding.txtTotal.setText(String.valueOf(Sum + Sum*0.1));

    }
}