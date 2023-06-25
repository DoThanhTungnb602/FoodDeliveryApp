package com.example.fooddeliveryapp.ui.auth;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.txtLoginForgetPassword.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_auth_to_forgotPasswordFragment);
        });

        binding.btnLogin.setOnClickListener(v ->

                loginAccountInFirebase()
        );


        return binding.getRoot();
    }

    void loginAccountInFirebase() {
        String email = binding.txtLoginEmail.getText().toString();
        String password= binding.txtLoginPassword.getText().toString();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        boolean isValidate = validateData(email,password);
        if(!isValidate){
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
//                            UserRepository userRepository = new UserRepository(database);
//                            FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
//                            String email = userFirebase.getEmail();
//                            User user = userRepository.getUserByEmail(email);
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.auth_to_home);
                            MainActivity.showNavView();
                        }else {
                            Toast.makeText(getActivity(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    boolean validateData(String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.txtLoginEmail.setError("Email is Invalid");
            return false;
        };
        if(password.length()<6){
            binding.txtLoginPassword.setError("Password length is Invalid");
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