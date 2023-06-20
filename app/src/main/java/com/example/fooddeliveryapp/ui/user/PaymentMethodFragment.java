package com.example.fooddeliveryapp.ui.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentPaymentMethodBinding;

import java.util.Objects;

public class PaymentMethodFragment extends Fragment {
    FragmentPaymentMethodBinding binding;
    AppDatabase database;
    UserRepository userRepository;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentMethodBinding.inflate(inflater, container, false);
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        userRepository = new UserRepository(database);
        PaymentMethod paymentMethod = userRepository.getPaymentMethod();

        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        if (paymentMethod.getBankName() != null) {
            binding.txtCardBankName.setText("Ngân hàng: " + paymentMethod.getBankName());
            binding.txtCardBankAccountNumber.setText("Số tài khoản: " + paymentMethod.getAccountNumber());
        }
        binding.txtBankOwnerName.setText(paymentMethod.getAccountName());
        binding.txtBankCCCD.setText(paymentMethod.getIdentifyNumber());
        binding.txtBankName.setText(paymentMethod.getBankName());
        binding.txtBankAccountNumber.setText(paymentMethod.getAccountNumber());

        binding.btnUpdatePaymentMethodConfirm.setOnClickListener(v -> {
            paymentMethod.setAccountName(binding.txtBankOwnerName.getText().toString());
            paymentMethod.setIdentifyNumber(binding.txtBankCCCD.getText().toString());
            paymentMethod.setBankName(binding.txtBankName.getText().toString());
            paymentMethod.setAccountNumber(binding.txtBankAccountNumber.getText().toString());
            userRepository.updatePaymentMethod(paymentMethod);
            Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        });

        return binding.getRoot();
    }
}