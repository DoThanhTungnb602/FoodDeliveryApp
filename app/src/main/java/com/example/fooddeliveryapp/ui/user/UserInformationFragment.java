package com.example.fooddeliveryapp.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentUserInformationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserInformationFragment extends Fragment {
    FragmentUserInformationBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        binding = FragmentUserInformationBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        UserRepository userRepository = new UserRepository(database);

        String email = userFirebase.getEmail();
        User user = userRepository.getUserByEmail(email);
        binding.edtUserEmail.setText(email);
        if (user.deliveryAddress==null){
            binding.edtAdress.setText("Hãy nhập địa chỉ!");
        }
        binding.edtAdress.setText(user.deliveryAddress);
        binding.edtName.setText(user.name);
        // Inflate the layout for this fragment



        binding.btnBack.setOnClickListener(v -> navController.popBackStack());
        if (user.image==null){
            binding.imgAvatar.setImageResource(R.mipmap.img_thien_dep_trai_foreground);
        }else {
            Glide.with(binding.imgAvatar.getContext()).load("https://loremflickr.com/g/320/240/paris").into(binding.imgAvatar);

        }
        binding.btnChangePassword.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInformationFragment_to_updatePasswordFragment);
        });
        binding.txtChangeInfor.setOnClickListener(v -> {
            binding.edtAdress.setEnabled(true);
            binding.btnUpdateUserInfor.setVisibility(View.VISIBLE);
            binding.btnUpdateUserInfor.setOnClickListener(v1 -> {
                user.setDeliveryAddress(binding.edtAdress.getText().toString());
                userRepository.updateUser(user);
                binding.edtAdress.setText(user.deliveryAddress);
                binding.btnUpdateUserInfor.setVisibility(View.GONE);
                binding.edtAdress.setEnabled(false);
            });
        });
        return binding.getRoot();

    }
}