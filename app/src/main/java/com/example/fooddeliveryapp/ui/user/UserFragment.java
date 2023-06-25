package com.example.fooddeliveryapp.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        UserRepository userRepository = new UserRepository(database);
        binding = FragmentUserBinding.inflate(inflater, container, false);
        MainActivity.showNavView();

        String email = userFirebase.getEmail();
        binding.txtEmailUser.setText(email);
        User user = userRepository.getUserByEmail(email);
        System.out.println(userRepository.getUserByEmail(email).name);
        binding.txtName.setText(user.name);
        binding.txtAdressUser.setText(user.deliveryAddress);
        if (user.image == null) {
            binding.imageView8.setImageResource(R.drawable.ic_user);
        } else {
            Glide.with(binding.imageView8.getContext()).load(user.image).into(binding.imageView8);
        }
        View root = binding.getRoot();
        binding.btnGoToCart.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_user_to_navigation_cart);
            MainActivity.hideNavView();
        });

        binding.btnUserInfor.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_user_to_userInformationFragment);
            MainActivity.hideNavView();
        });
        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_auth);
        });
        binding.btnUpdatePaymentMethod.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_user_to_paymentMethodFragment);
            MainActivity.hideNavView();
        });

        PaymentMethod paymentMethod = userRepository.getPaymentMethod();
        if (Objects.equals(paymentMethod.methodType, "Payment on delivery")) {
            binding.btnPaymentOnDelivery.setChecked(true);
        } else {
            binding.btnPaymentBank.setChecked(true);
        }

        binding.btnPaymentOnDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.btnPaymentBank.setChecked(false);
                paymentMethod.setMethodType("Payment on delivery");
                Toast.makeText(getContext(), "Payment on delivery", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        binding.btnPaymentBank.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.btnPaymentOnDelivery.setChecked(false);
                paymentMethod.setMethodType("Bank account");
                Toast.makeText(getContext(), "Bank account", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}