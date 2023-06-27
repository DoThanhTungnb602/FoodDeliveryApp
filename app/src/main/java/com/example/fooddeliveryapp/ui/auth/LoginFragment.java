package com.example.fooddeliveryapp.ui.auth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
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
    private UserRepository userRepository;
    private FirebaseAuth firebaseAuth;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        userRepository = new UserRepository(AppDatabase.getDatabase(requireActivity()));


        binding.txtLoginForgetPassword.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_auth_to_forgotPasswordFragment);
        });

        binding.btnLogin.setOnClickListener(v -> {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = getActivity().getCurrentFocus();
                    if (view == null) {
                        view = new View(getActivity());
                    }
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    loginAccountInFirebase();
                    user = userRepository.getUserByEmail(binding.txtLoginEmail.getText().toString());
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("loggedUserID", user.getId());
                    editor.apply();
                    MainActivity.currentUserID = user.getId();
                }
        );


        return binding.getRoot();
    }

    void loginAccountInFirebase() {
        String email = binding.txtLoginEmail.getText().toString();
        String password = binding.txtLoginPassword.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        boolean isValidate = validateData(email, password);
        if (!isValidate) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            UserRepository userRepository = new UserRepository(database);
//                            FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
//                            String email = userFirebase.getEmail();
//                            User user = userRepository.getUserByEmail(email);
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.auth_to_home);
                            MainActivity.showNavView();
                        } else {
                            Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không đúng, vui lòng nhập lại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    boolean validateData(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtLoginEmail.setError("Email không hợp lệ");
            return false;
        }
        if (password.length() < 6) {
            binding.txtLoginPassword.setError("Độ dài mật khẩu không hợp lệ");
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