package com.example.fooddeliveryapp.ui.food;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Favorite;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.FavoriteRepository;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.repositories.CartRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentFoodDetailsBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoodDetailsFragment extends Fragment {
    private FragmentFoodDetailsBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private FoodRepository foodRepository;
    private CartRepository cartRepository;
    private FavoriteRepository favoriteRepository;
    private Cart cart;
    private List<Cart> listCart;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.hideNavView();


        AppDatabase database = AppDatabase.getDatabase(requireActivity());
//        UserRepository userRepository = new UserRepository(database);
//        FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
//        String email = userFirebase.getEmail();
//        User user = userRepository.getUserByEmail(email);


        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        //Tạo các list
        foodRepository = new FoodRepository(AppDatabase.getDatabase(requireActivity()));
        cartRepository = new CartRepository(AppDatabase.getDatabase(requireActivity()));
        favoriteRepository = new FavoriteRepository(AppDatabase.getDatabase(requireActivity()));
//        List<Favorite> favoriteList = favoriteRepository.getFavoriteList();
//        favoriteList
//        listCart = new ArrayList<>();

        // Tạo slide trong food detail
        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();
        assert getArguments() != null;
        Food food = foodRepository.getFoodById(getArguments().getInt("food_id"));
        List<FoodImage> ListImage = food.getFoodImages();
        for (int i = 0; i < ListImage.size(); i++) {
            imageList.add(new SlideModel(ListImage.get(i).imageUrl, ScaleTypes.CENTER_INSIDE));
        }
        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList);


        // Hiển thị tên món ăn
        binding.txtFoodDetailsTitle.setText(food.name);

        // Hiển thị đánh giá
        binding.txtFoodDetailRating.setText(String.valueOf(food.averageRating));


        // Tạo formart cho tiền
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Hiển thị giá đồ ăn
        binding.txtFoodDetailsPrice.setText(currencyFormat.format(food.price));

        // Tính thời gian giao tới
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, food.deliveryTime);
        DateFormat currentTime = new SimpleDateFormat("HH:mm");
        binding.txtFoodDetailsSuccess.setText("Dự kiến giao lúc " + currentTime.format(cal.getTime()));

        // Hiển thị khoảng thời gian giao đến
        binding.txtFoodDetailsDeliveryInfo.setText(food.deliveryTime + " phút");

        // Hiển thị địa chỉ cửa hàng
        binding.txtFoodDetailsStoreInfo.setText(foodRepository.getRestaurant(food).name);

        binding.btnGoToCart2.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart));
        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        binding.btnAddToCart.setOnClickListener(v -> {
//            Implement add to cart
            if (cartRepository.isExist(food.id, MainActivity.currentUserID) == 0) {
                Cart cart = new Cart(MainActivity.currentUserID, food.id, 1);
                cartRepository.insertCart(cart);
            } else {
                cart = cartRepository.getCartByFoodId(food.id);
                cart.setQuantity(cart.getQuantity() + 1);
                cartRepository.updateCart(cart);
            }
            Toast.makeText(this.getContext(), "Bạn đã thêm món " + food.name + " vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
        });
        Favorite favorite = new Favorite(food.id, MainActivity.currentUserID);
        if (favoriteRepository.isExist(food.id) != 0) {
            binding.toggleButton.setChecked(true);
        }
        binding.toggleButton.setOnClickListener(v -> {

            if (binding.toggleButton.isChecked()) {
                Toast.makeText(getContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                favoriteRepository.insertFavorite(favorite);
//                System.out.println(favoriteRepository.getFavoriteList().get(0).foodId);
            } else {
                Toast.makeText(getContext(), "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                favoriteRepository.deleteFavorite(favorite);
            }
        });
        return root;
    }
}