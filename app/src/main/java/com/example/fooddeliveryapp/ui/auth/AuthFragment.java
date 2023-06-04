package com.example.fooddeliveryapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentAuthBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AuthFragment extends Fragment {
    private FragmentAuthBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);

        // Hide bottom navigation view
        MainActivity.hideNavView();

        AuthViewPagerAdapter authViewPagerAdapter = new AuthViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        binding.viewPagerAuth.setAdapter(authViewPagerAdapter);

        new TabLayoutMediator(binding.tabLayoutAuth, binding.viewPagerAuth, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Đăng nhập");
                    break;
                case 1:
                    tab.setText("Đăng ký");
                    break;
            }
        }).attach();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}