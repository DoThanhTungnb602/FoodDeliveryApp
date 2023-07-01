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
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        // Khởi tạo các biến
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

        // Gán adapter cho recycler view
        recyclerViewCheckout.setAdapter(checkoutAdapter);

        // Tính tổng tiền
        int totalPrice = 0;
        user = userRepository.getCurrentUser();
        PaymentMethod paymentMethod = userRepository.getPaymentMethod();
        for (Cart cart : cartList) {
            Food food = foodRepository.getFoodById(cart.getFoodId());
            totalPrice += food.getPrice() * cart.getQuantity();
        }

        // Gán giá trị tổng tiền
        binding.txtCheckoutTotalPrice.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice));
        // Gán giá trị thuế
        binding.txtCheckoutTax.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice * 0.1));
        // Gán địa chỉ giao hàng
        if (user.deliveryAddress != null) {
            binding.txtCheckoutAddress.setText("Địa chỉ giao hàng: " + user.deliveryAddress);
        }

        // Gán phương thức thanh toán
        if (Objects.equals(paymentMethod.methodType, "Payment on delivery")) {
            binding.btnCheckoutPaymentOnDelivery.setChecked(true);
        } else {
            binding.btnCheckoutPaymentBank.setChecked(true);
        }
        if (paymentMethod.getBankName() == null) {
            binding.btnCheckoutPaymentBank.setEnabled(false);
        }

        // Xử lý sự kiện khi chọn phương thức thanh toán
        binding.btnCheckoutPaymentOnDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                paymentMethod.setMethodType("Payment on delivery");
                Toast.makeText(getContext(), "Payment on delivery", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        // Xử lý sự kiện khi chọn phương thức thanh toán
        binding.btnCheckoutPaymentBank.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                paymentMethod.setMethodType("Bank account");
                Toast.makeText(getContext(), "Bank account", Toast.LENGTH_SHORT).show();
                userRepository.updatePaymentMethod(paymentMethod);
            }
        });

        // Xử lý sự kiện khi chọn thay đổi địa chỉ giao hàng
        binding.btnCheckoutChangeAddress.setOnClickListener(v -> {
            // Chuyển sang màn hình cập nhật thông tin người dùng
            navController.navigate(R.id.userInformationFragment);
        });

        // Xử lý sự kiện khi click vào nút đặt hàng
        int finalTotalPrice = totalPrice;
        binding.btnCheckout.setOnClickListener(v -> {
            // Kiểm tra xem người dùng đã cập nhật địa chỉ giao hàng chưa
            if (user.getDeliveryAddress() == null) {
                Toast.makeText(getContext(), "Vui lòng cập nhật địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            } else {
                // Tính tổng tiền với thuế
                int priceWithTax = (int) (finalTotalPrice + (finalTotalPrice * 0.1));
                // Nếu đã cập nhật địa chỉ giao hàng thì đặt hàng thành công và chuyển về màn hình chính
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                cartRepository.deleteAllCart();
                orderRepository.insertOrder("Đang xử lý", priceWithTax, cartList);
                navController.navigate(R.id.navigation_home);
            }
        });

        // Xử lý sự kiện khi click vào nút quay lại
        binding.btnBack.setOnClickListener(v -> {
            navController.popBackStack();
        });

        return binding.getRoot();
    }

    // Adapter
    private static class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
        // Danh sách các món ăn trong giỏ hàng
        List<Cart> listCart;
        // Các repository
        FoodRepository foodRepository = new FoodRepository(AppDatabase.getDatabase(null));
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Hàm khởi tạo
        public CheckoutAdapter(List<Cart> listCart) {
            this.listCart = listCart;
        }

        @NonNull
        @Override
        public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Gán layout cho view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
            return new CheckoutViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
            // Lấy ra món ăn tại vị trí position
            Cart cart = listCart.get(position);
            // Lấy ra món ăn tương ứng với id của món ăn trong giỏ hàng
            Food food = foodRepository.getFoodById(cart.getFoodId());
            // Gán dữ liệu cho tên món ăn
            holder.txtCheckoutItemTitle.setText(food.getName());
            // Gán dữ liệu cho giá món ăn
            holder.txtCheckoutItemPrice.setText(currencyFormatter.format(food.getPrice()));
            // Gán dữ liệu cho số lượng món ăn
            holder.txtCheckoutItemQuantity.setText("Số lượng: " + cart.getQuantity());
            // Gán dữ liệu cho hình ảnh món ăn
            String imageUrl = food.getFoodImages().get(0).imageUrl;
            // Load hình ảnh bằng Glide
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
                // Ánh xạ view
                imgCheckout = itemView.findViewById(R.id.imgCheckoutItem);
                txtCheckoutItemTitle = itemView.findViewById(R.id.txtCheckoutItemTitle);
                txtCheckoutItemPrice = itemView.findViewById(R.id.txtCheckoutItemPrice);
                txtCheckoutItemQuantity = itemView.findViewById(R.id.txtCheckoutItemQuantity);
            }
        }
    }
}