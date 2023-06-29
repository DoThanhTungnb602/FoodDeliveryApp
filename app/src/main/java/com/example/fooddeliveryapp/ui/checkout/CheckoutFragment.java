package com.example.fooddeliveryapp.ui.checkout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.CartRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.OrderRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentCheckoutBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class CheckoutFragment extends Fragment {
    FragmentCheckoutBinding binding;
    AppDatabase database;
    UserRepository userRepository;
    CartRepository cartRepository;
    FoodRepository foodRepository;
    OrderRepository orderRepository;
    RecyclerView recyclerViewCheckout;
    NavController navController;
    NavHostFragment navHostFragment;
    List<Cart> cartList;
    User user;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        database = AppDatabase.getDatabase(requireActivity());
        userRepository = new UserRepository(database);
        cartRepository = new CartRepository(database);
        foodRepository = new FoodRepository(database);
        orderRepository = new OrderRepository(database);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        recyclerViewCheckout = binding.recyclerViewCheckoutList;

        // Set up recycler view
        cartList = cartRepository.getAllCart();
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(cartList);
        recyclerViewCheckout.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewCheckout.setAdapter(checkoutAdapter);

        // Set up total price
        int totalPrice = 0;
        user = userRepository.getCurrentUser();
        PaymentMethod paymentMethod = userRepository.getPaymentMethod();
        for (Cart cart : cartList) {
            Food food = foodRepository.getFoodById(cart.getFoodId());
            totalPrice += food.getPrice() * cart.getQuantity();
        }

        binding.txtCheckoutTotalPrice.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice));
        binding.txtCheckoutTax.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice * 0.1));
        if (user.deliveryAddress != null) {
            binding.txtCheckoutAddress.setText("Địa chỉ thanh toán: " + user.deliveryAddress);
        }

        if (Objects.equals(paymentMethod.methodType, "Payment on delivery")) {
            binding.btnCheckoutPaymentOnDelivery.setChecked(true);
        } else {
            binding.btnCheckoutPaymentBank.setChecked(true);
        }

        // Set up button
        if (paymentMethod.getBankName() == null) {
            binding.btnCheckoutPaymentBank.setEnabled(false);
        }
        binding.btnCheckoutPaymentOnDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                paymentMethod.setMethodType("Payment on delivery");
                Toast.makeText(getContext(), "Payment on delivery", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        binding.btnCheckoutPaymentBank.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                paymentMethod.setMethodType("Bank account");
                Toast.makeText(getContext(), "Bank account", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        binding.btnCheckoutChangeAddress.setOnClickListener(v -> {
            navController.navigate(R.id.userInformationFragment);
        });

        int finalTotalPrice = totalPrice;
        binding.btnCheckout.setOnClickListener(v -> {
            if (user.getDeliveryAddress() == null) {
                Toast.makeText(getContext(), "Vui lòng cập nhật địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                int priceWithTax = (int) (finalTotalPrice + (finalTotalPrice * 0.1));
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                cartRepository.deleteAllCart();
                orderRepository.insertOrder("Đang xử lý", priceWithTax, cartList);
                navController.navigate(R.id.navigation_home);
            }
        });


        binding.btnBack.setOnClickListener(v -> {
            navController.popBackStack();
        });

        return binding.getRoot();
    }

    private static class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
        List<Cart> listCart;
        FoodRepository foodRepository = new FoodRepository(AppDatabase.getDatabase(null));
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        public CheckoutAdapter(List<Cart> listCart) {
            this.listCart = listCart;
        }

        @NonNull
        @Override
        public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
            return new CheckoutViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
            Cart cart = listCart.get(position);
            Food food = foodRepository.getFoodById(cart.getFoodId());
            holder.txtCheckoutItemTitle.setText(food.getName());
            holder.txtCheckoutItemPrice.setText(currencyFormatter.format(food.getPrice()));
            holder.txtCheckoutItemQuantity.setText("Số lượng: " + cart.getQuantity());
            String imageUrl = food.getFoodImages().get(0).imageUrl;
            Glide.with(holder.imgCheckout.getContext()).load(imageUrl).fitCenter().into(holder.imgCheckout);
        }

        @Override
        public int getItemCount() {
            return listCart.size();
        }

        public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
            ImageView imgCheckout;
            TextView txtCheckoutItemTitle;
            TextView txtCheckoutItemPrice;
            TextView txtCheckoutItemQuantity;

            public CheckoutViewHolder(@NonNull View itemView) {
                super(itemView);
                imgCheckout = itemView.findViewById(R.id.imgCheckoutItem);
                txtCheckoutItemTitle = itemView.findViewById(R.id.txtCheckoutItemTitle);
                txtCheckoutItemPrice = itemView.findViewById(R.id.txtCheckoutItemPrice);
                txtCheckoutItemQuantity = itemView.findViewById(R.id.txtCheckoutItemQuantity);
            }
        }
    }
}