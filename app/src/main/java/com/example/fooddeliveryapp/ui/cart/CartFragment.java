package com.example.fooddeliveryapp.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentCartBinding;


public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private CartViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}