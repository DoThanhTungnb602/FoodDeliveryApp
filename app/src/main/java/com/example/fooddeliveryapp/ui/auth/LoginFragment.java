package com.example.fooddeliveryapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.txtLoginForgetPassword.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_auth_to_forgotPasswordFragment);
        });
        binding.btnLogin.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.auth_to_home));


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}