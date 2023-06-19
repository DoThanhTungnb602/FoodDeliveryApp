package com.example.fooddeliveryapp;


import android.content.Context;

import androidx.annotation.NonNull;


import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.dao.RestaurantDao;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.CategoryRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FakeData {

    public List<Restaurant> getRestaurant() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("KFC", "12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Hồ Chí Minh"));
        restaurants.add(new Restaurant("Pizza Hut", "175 Định Công, Phường Định Công, Quận Hoàng Mai, Hà Nội"));
        restaurants.add(new Restaurant("Burger King", "Tầng 1, TTTM Vincom Center, 72 Lê Thánh Tôn, Bến Nghé, Quận 1, Hồ Chí Minh"));
        restaurants.add(new Restaurant("Lotteria", "20 Tây Sơn, Quận Đống Đa, Hà Nội"));
        restaurants.add(new Restaurant("Jollibee", "Tầng 1, TTTM Vincom Center, 72 Lê Thánh Tôn, Bến Nghé, Quận 1, Hồ Chí Minh"));
        return restaurants;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Đỗ Thanh Tùng", "Dothanhtungnb602@gmail.com", "Tung2001"));
        return users;
    }

    public void fetchDataFromServerToDatabase(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        FoodRepository foodRepository = new FoodRepository(database);
        CategoryRepository categoryRepository = new CategoryRepository(database);
        RestaurantDao restaurantDao = database.restaurantDao();
        UserRepository userRepository = new UserRepository(database);
        List<Food> foods = foodRepository.getAllFood();
        List<Category> categories = new ArrayList<>();
        if (foods.size() == 0) {
            for (int i = 0; i < 20; i++) {
                foodRepository.getFoodFromServer().thenAccept(foodRepository::insertFood);
            }
            categoryRepository.getCategoryFromServer().thenAccept(categoryRepository::insertAllCategories);
            restaurantDao.insertAll(getRestaurant());
            userRepository.insertUsers(getUsers());
        }
    }

    public void resetData(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        FoodRepository foodRepository = new FoodRepository(database);
        CategoryRepository categoryRepository = new CategoryRepository(database);
        RestaurantDao restaurantDao = database.restaurantDao();
        UserRepository userRepository = new UserRepository(database);
        foodRepository.deleteAllFood();
        categoryRepository.deleteAllCategory();
        restaurantDao.deleteAll();
        userRepository.deleteAllUser();
    }
}
