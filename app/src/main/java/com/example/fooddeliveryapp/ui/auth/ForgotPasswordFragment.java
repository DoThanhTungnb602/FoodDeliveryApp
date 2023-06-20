package com.example.fooddeliveryapp.ui.auth;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        binding.btnBackToLogin.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_forgotPasswordFragment_to_navigation_auth));
        binding.btnForgotPassword.setOnClickListener(v -> {
            if (validateEmail()) resetpassword();
        });
        return binding.getRoot();
    }
    void resetpassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailaddress = binding.txtLoginEmail.getText().toString();
        auth.sendPasswordResetEmail(emailaddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getActivity(), "Check Your Email", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_auth);
                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private boolean validateEmail() {
        String email = binding.txtLoginEmail.getText().toString().trim();
        if (email.isEmpty()) {
            binding.txtLoginEmail.setError("Không được để trống");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtLoginEmail.setError("Email không hợp lệ");
            return false;
        } else {
            binding.txtLoginEmail.setError(null);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}