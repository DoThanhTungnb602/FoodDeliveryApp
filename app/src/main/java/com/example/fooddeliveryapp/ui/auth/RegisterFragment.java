package com.example.fooddeliveryapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}