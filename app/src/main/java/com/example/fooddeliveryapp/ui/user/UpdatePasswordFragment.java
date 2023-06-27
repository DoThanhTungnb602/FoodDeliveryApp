package com.example.fooddeliveryapp.ui.user;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentUpdatePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordFragment extends Fragment {
    FragmentUpdatePasswordBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


        binding.btnBack.setOnClickListener(v -> navController.popBackStack());
        binding.btnChangePass.setOnClickListener(v->
                changePass()
                );

        return binding.getRoot();
    }
    void changePass(){
        String email = user.getEmail();
        String newPassword = binding.textInputLayout.getEditText().getText().toString();
        String oldPassword = binding.textInputLayout1.getEditText().getText().toString();
        String confirmNewPassword = binding.textInputLayout2.getEditText().getText().toString();
        boolean isValidate = validateData(newPassword,confirmNewPassword);
        if (!isValidate){
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(),"Change password successfully",Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_user);
                            }else {
                                Toast.makeText(getActivity(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean validateData(String newPassword, String confirmNewPassword){
        if(!newPassword.equals(confirmNewPassword)){
            binding.textInputLayout2.getEditText().setError("Password length is Invalid");
            return false;
        }
        return true;
    }
}