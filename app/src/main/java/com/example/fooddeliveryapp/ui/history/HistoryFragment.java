package com.example.fooddeliveryapp.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentHistoryBinding;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.showNavView();

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.history_to_cart);
            MainActivity.hideNavView();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}