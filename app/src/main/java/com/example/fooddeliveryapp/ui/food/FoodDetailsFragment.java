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
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.databinding.FragmentFoodDetailsBinding;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FoodDetailsFragment extends Fragment {
    private FragmentFoodDetailsBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private FoodRepository foodRepository;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        foodRepository = new FoodRepository(AppDatabase.getDatabase(requireActivity()));
//        Bundle args = getArguments();
//        int foodId = 0;
//        if (args != null) {
//            foodId = args.getInt("foodId");
//            // Use the foodId as needed
//        }
//        System.out.print(foodId);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();

        imageList.add(new SlideModel("https://loremflickr.com/200/300/food", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://loremflickr.com/200/300/food", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://loremflickr.com/200/300/food", "And people do that.", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList);

        // Hiển thị tên món ăn
        TextView txtFoodDetailsTitle = binding.txtFoodDetailsTitle;
        txtFoodDetailsTitle.setText(foodRepository.getFoodById(getArguments().getInt("food_id")).name);

        // Hiển thị đánh giá
        TextView txtFoodDetailRating = binding.txtFoodDetailRating;
        txtFoodDetailRating.setText(String.valueOf(foodRepository.getFoodById(getArguments().getInt("food_id")).averageRating));

        // Hiển thị giá đồ ăn
        TextView txtFoodDetailsPrice = binding.txtFoodDetailsPrice;
        txtFoodDetailsPrice.setText(String.valueOf(foodRepository.getFoodById(getArguments().getInt("food_id")).price));

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
        txtFoodDetailsStoreInfo.setText(foodRepository.getFoodById(getArguments().getInt("food_id")).description);

        binding.btnGoToCart2.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart));
        binding.btnBack.setOnClickListener(v -> navController.popBackStack());

        binding.btnAddToCart.setOnClickListener(v -> {
//            Implement add to cart
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_foodDetailsFragment_to_navigation_cart);
        });

        return root;
    }

}