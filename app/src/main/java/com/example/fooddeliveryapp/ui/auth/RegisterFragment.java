package com.example.fooddeliveryapp.ui.auth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        binding.buttonRegister.setOnClickListener(v->
                createAccount());
        return binding.getRoot();
    }

    void createAccount() {
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String confirmPassword = binding.edtConfirmPassword.getText().toString();


        boolean isValidate = validateData(email,password,confirmPassword);
        if(!isValidate){
            return;
        }
        createAccountInFirebase(email,password);
    }
    void createAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userFullname = binding.edtFullname.getText().toString();
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        UserRepository userRepository = new UserRepository(database);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                (Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User newUser = new User(userFullname,email,password);
                            userRepository.insertUser(newUser);
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            Toast.makeText(getActivity(), "Register successfully, please check email to verify", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_auth);
                        }else {
                            Toast.makeText(getActivity(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    boolean validateData(String email,String password, String confirmPassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmail.setError("Email is Invalid");
            return false;
        };
        if(password.length()<6){
            binding.edtPassword.setError("Password length is Invalid");
            return false;
        }
        if(!password.equals(confirmPassword)){
            binding.edtConfirmPassword.setError("Password not match");
            return false;
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}