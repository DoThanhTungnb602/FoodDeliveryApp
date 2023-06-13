package com.example.fooddeliveryapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentUserInformationBinding;


public class UserInformationFragment extends Fragment {
    FragmentUserInformationBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserInformationBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        binding.btnBack.setOnClickListener(v -> navController.popBackStack());
        binding.imgAvatar.setImageResource(R.mipmap.img_thien_dep_trai_foreground);

        binding.btnChangePassword.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInformationFragment_to_updatePasswordFragment);
        });

        return binding.getRoot();

    }
}