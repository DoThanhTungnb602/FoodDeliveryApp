package com.example.fooddeliveryapp.ui.cart;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.CartTable;
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
    private static List<CartTable> listCart;
    private List<Cart> listCart;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private AppDatabase database;
    private CartTable cart;
    private KeyEvent event;

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
        listCart = new ArrayList<>();

        // Cart là giỏ hàng

        System.out.print(listCart.size());

        // thêm các list
        CartRepository cartRepository = new CartRepository(database);
        FoodRepository foodRepository = new FoodRepository(database);

        // Xử lý thêm vào giỏ hàng
        Food food = foodRepository.getFoodById(getArguments().getInt("food_id"));
        System.out.println(food.id);

        if (cartRepository.isExist(food.id) == 0) {
            cartRepository.insertCart(food.id, 0);
            cart = cartRepository.getCartByFoodId(food.id);
            listCart.add(cart);
            System.out.println("Size of ListCart: " + listCart.size());
            System.out.println("food id from CartFragment: " + cart.foodId);
            System.out.println("quantity from CartFragment: " + cart.quantity);
        } else {
            cart = cartRepository.getCartByFoodId(food.id);
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.updateCart(cartRepository.getCartByFoodId(food.id));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartListAdapter = new CartListAdapter(listCart);
        recyclerView.setAdapter(cartListAdapter);

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

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}