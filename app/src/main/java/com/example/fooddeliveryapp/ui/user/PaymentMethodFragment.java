package com.example.fooddeliveryapp.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentPaymentMethodBinding;

public class PaymentMethodFragment extends Fragment {
    FragmentPaymentMethodBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentMethodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}