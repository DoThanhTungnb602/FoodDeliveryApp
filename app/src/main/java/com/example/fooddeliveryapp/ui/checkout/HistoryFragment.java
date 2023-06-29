package com.example.fooddeliveryapp.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Order;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.OrderRepository;
import com.example.fooddeliveryapp.databinding.FragmentHistoryBinding;

import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private OrderRepository orderRepository;
    private FoodRepository foodRepository;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.showNavView();

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.history_to_cart);
            MainActivity.hideNavView();
        });

        orderRepository = new OrderRepository(AppDatabase.getDatabase(requireActivity()));
        foodRepository = new FoodRepository(AppDatabase.getDatabase(requireActivity()));
        orderList = orderRepository.getOrderList();
        if (orderList.size() == 0) {
            binding.layoutNoOrder.setVisibility(View.VISIBLE);
        } else {
            binding.layoutNoOrder.setVisibility(View.GONE);
            recyclerView = binding.recyclerViewHistory;
            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
            orderAdapter = new OrderAdapter(orderList, orderRepository, foodRepository);
            recyclerView.setAdapter(orderAdapter);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}