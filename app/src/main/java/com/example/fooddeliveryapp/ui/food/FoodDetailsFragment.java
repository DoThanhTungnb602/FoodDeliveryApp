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
        Food food = foodRepository.getFoodById(getArguments().getInt("food_id"));
        List<FoodImage> ListImage = food.getFoodImages();
        for(int i=0; i<ListImage.size(); i++){
            imageList.add(new SlideModel(ListImage.get(i).imageUrl, ScaleTypes.CENTER_INSIDE));
        }
        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList);


        // Hiển thị tên món ăn
        TextView txtFoodDetailsTitle = binding.txtFoodDetailsTitle;
        txtFoodDetailsTitle.setText(food.name);

        // Hiển thị đánh giá
        TextView txtFoodDetailRating = binding.txtFoodDetailRating;
        txtFoodDetailRating.setText(String.valueOf(food.averageRating));


        // Tạo formart cho tiền
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Hiển thị giá đồ ăn
        TextView txtFoodDetailsPrice = binding.txtFoodDetailsPrice;
        txtFoodDetailsPrice.setText(String.valueOf(currencyFormat.format(food.price)));

        // Tính thời gian giao tới
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, foodRepository.getFoodById(getArguments().getInt("food_id")).deliveryTime);
        DateFormat currentTime = new SimpleDateFormat("HH:mm");
        TextView txtFoodDetailsSuccess = binding.txtFoodDetailsSuccess;
        txtFoodDetailsSuccess.setText("Dự kiến giao lúc " + currentTime.format(cal.getTime()));

        // Hiển thị khoảng thời gian giao đến
        TextView txtFoodDetailsDeliveryInfo = binding.txtFoodDetailsDeliveryInfo;
        txtFoodDetailsDeliveryInfo.setText(String.valueOf(foodRepository.getFoodById(getArguments().getInt("food_id")).deliveryTime) + " phút");

        // Hiển thị địa chỉ cửa hàng
        TextView txtFoodDetailsStoreInfo = binding.txtFoodDetailsStoreInfo;
        txtFoodDetailsStoreInfo.setText(foodRepository.getRestaurant(food).name);

        binding.btnGoToCart2.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart));
        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        binding.btnAddToCart.setOnClickListener(v -> {
//            Implement add to cart
            if(cartRepository.isExist(food.id)==0) {
                cartRepository.insertCart(food.id, 1);
            }else {
                cart = cartRepository.getCartByFoodId(food.id);
                cart.setQuantity(cart.getQuantity() + 1);
                cartRepository.updateCart(cart);
            }
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart);
        });
        Favorite favorite = new Favorite(food.id, MainActivity.currentUserID);
        if(favoriteRepository.isExist(food.id)!=0){
            binding.toggleButton.setChecked(true);
        }
        binding.toggleButton.setOnClickListener(v->{

            if(binding.toggleButton.isChecked()){
                favoriteRepository.insertFavorite(favorite);
//                System.out.println(favoriteRepository.getFavoriteList().get(0).foodId);
            }else {
                favoriteRepository.deleteFavorite(favorite);
            }
        });
        return root;
    }
}