package com.example.fooddeliveryapp;


import android.content.Context;


import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.PaymentMethodDao;
import com.example.fooddeliveryapp.data.db.dao.RestaurantDao;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.CategoryRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        users.add(new User("Nguyễn Đức Thiện", "thien14112002@gmail.com", "123456"));
        users.add(new User("Nguyen Huy Hoang", "hoang001359@gmail.com", "123456"));
        return users;
    }

    public void fetchDataFromServerToDatabase(Context context) {
        AppDatabase database = AppDatabase.getDatabase(context);
        FoodRepository foodRepository = new FoodRepository(database);
        CategoryRepository categoryRepository = new CategoryRepository(database);
        RestaurantDao restaurantDao = database.restaurantDao();
        UserRepository userRepository = new UserRepository(database);
        PaymentMethodDao paymentMethodDao = database.paymentMethodDao();
        List<Food> foods = foodRepository.getAllFood();
        if (foods.size() == 0) {
            for (int i = 0; i < 20; i++) {
                foodRepository.getFoodFromServer().thenAccept(foodRepository::insertFood);
            }
            categoryRepository.getCategoryFromServer().thenAccept(data -> {
            });
            restaurantDao.insertAll(getRestaurant());
            userRepository.insertUsers(getUsers());
            paymentMethodDao.insertPaymentMethod(new PaymentMethod(MainActivity.currentUserID, "Payment on delivery"));
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
