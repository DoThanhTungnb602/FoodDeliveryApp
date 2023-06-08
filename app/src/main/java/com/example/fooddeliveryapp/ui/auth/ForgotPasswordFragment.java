package com.example.fooddeliveryapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentForgotPasswordBinding;

public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        binding.btnBackToLogin.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_forgotPasswordFragment_to_navigation_auth));
        binding.txtConfirmPassword.setVisibility(View.GONE);
        binding.btnForgotPassword.setOnClickListener(v -> {
            if (validateEmail()) showSendOTP();
        });
        return binding.getRoot();
    }

    private void showSendOTP() {
        binding.txtForgotPassword.setText("");
        binding.txtForgotPassword.setHint("Mã OTP");
        binding.txtConfirmPassword.setVisibility(View.GONE);
        binding.btnForgotPassword.setText("Xác nhận");
        binding.btnForgotPassword.setOnClickListener(v -> {
            if (validateOTP()) showChangePassword();
        });
    }

    private void showChangePassword() {
        binding.txtForgotPassword.setHint("Mật khẩu mới");
        binding.txtConfirmPassword.setVisibility(View.VISIBLE);
        binding.btnForgotPassword.setText("Đổi mật khẩu");
        binding.btnForgotPassword.setOnClickListener(v -> {
            if (validatePassword() && changePassword())
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_forgotPasswordFragment_to_navigation_auth);
        });
    }

    private boolean validateOTP() {
        // Implement this function
        return true;
    }

    private boolean validatePassword() {
        String password = binding.txtForgotPassword.getText().toString().trim();
        String confirmPassword = binding.txtConfirmPassword.getText().toString().trim();
        if (password.isEmpty()) {
            binding.txtForgotPassword.setError("Không được để trống");
            return false;
        } else if (password.length() < 6) {
            binding.txtForgotPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        } else if (!password.equals(confirmPassword)) {
            binding.txtConfirmPassword.setError("Mật khẩu không trùng khớp");
            return false;
        } else {
            binding.txtForgotPassword.setError(null);
            return true;
        }
    }

    private boolean changePassword() {
        // Implement this function
        return true;
    }

    private boolean validateEmail() {
        String email = binding.txtForgotPassword.getText().toString().trim();
        if (email.isEmpty()) {
            binding.txtForgotPassword.setError("Không được để trống");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtForgotPassword.setError("Email không hợp lệ");
            return false;
        } else {
            binding.txtForgotPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}