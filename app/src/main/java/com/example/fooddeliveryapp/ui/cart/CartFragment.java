package com.example.fooddeliveryapp.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.databinding.FragmentCartBinding;

import java.util.List;


public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;
    private List<Cart> listCart;
    private NavController navController;
    private NavHostFragment navHostFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerViewCartList;
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

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

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}