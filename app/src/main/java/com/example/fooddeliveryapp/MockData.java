package com.example.fooddeliveryapp;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.dao.RestaurantDao;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;
import com.example.fooddeliveryapp.data.repositories.CategoryRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockData {
    List<Food> foods = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    List<FoodImage> foodImages = new ArrayList<>();
    List<Restaurant> restaurants = new ArrayList<>();
    CategoryRepository categoryRepository;
    FoodRepository foodRepository;
    RestaurantDao restaurantDao;
    FoodImageDao foodImageDao;

    public MockData() {
        List<FoodImage> foodImages1 = new ArrayList<>();
        foodImages1.add(new FoodImage("https://bit.ly/2YoJ77H", 1));
        foodImages1.add(new FoodImage("https://bit.ly/2BteuF2", 1));
        foodImages1.add(new FoodImage("https://bit.ly/3fLJf72", 1));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 1));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 1));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 1));

        List<FoodImage> foodImages2 = new ArrayList<>();
        foodImages2.add(new FoodImage("https://bit.ly/2YoJ77H", 2));
        foodImages2.add(new FoodImage("https://bit.ly/2BteuF2", 2));
        foodImages2.add(new FoodImage("https://bit.ly/3fLJf72", 2));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 2));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 2));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 2));

        List<FoodImage> foodImages3 = new ArrayList<>();
        foodImages3.add(new FoodImage("https://bit.ly/2YoJ77H", 3));
        foodImages3.add(new FoodImage("https://bit.ly/2BteuF2", 3));
        foodImages3.add(new FoodImage("https://bit.ly/3fLJf72", 3));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 3));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 3));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 3));

        List<FoodImage> foodImages4 = new ArrayList<>();
        foodImages4.add(new FoodImage("https://bit.ly/2YoJ77H", 4));
        foodImages4.add(new FoodImage("https://bit.ly/2BteuF2", 4));
        foodImages4.add(new FoodImage("https://bit.ly/3fLJf72", 4));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 4));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 4));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 4));

        List<FoodImage> foodImages5 = new ArrayList<>();
        foodImages5.add(new FoodImage("https://bit.ly/2YoJ77H", 5));
        foodImages5.add(new FoodImage("https://bit.ly/2BteuF2", 5));
        foodImages5.add(new FoodImage("https://bit.ly/3fLJf72", 5));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 5));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 5));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 5));

        List<FoodImage> foodImages6 = new ArrayList<>();
        foodImages6.add(new FoodImage("https://bit.ly/2YoJ77H", 6));
        foodImages6.add(new FoodImage("https://bit.ly/2BteuF2", 6));
        foodImages6.add(new FoodImage("https://bit.ly/3fLJf72", 6));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 6));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 6));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 6));

        List<FoodImage> foodImages7 = new ArrayList<>();
        foodImages7.add(new FoodImage("https://bit.ly/2YoJ77H", 7));
        foodImages7.add(new FoodImage("https://bit.ly/2BteuF2", 7));
        foodImages7.add(new FoodImage("https://bit.ly/3fLJf72", 7));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 7));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 7));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 7));

        List<FoodImage> foodImages8 = new ArrayList<>();
        foodImages8.add(new FoodImage("https://bit.ly/2YoJ77H", 8));
        foodImages8.add(new FoodImage("https://bit.ly/2BteuF2", 8));
        foodImages8.add(new FoodImage("https://bit.ly/3fLJf72", 8));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 8));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 8));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 8));

        List<FoodImage> foodImages9 = new ArrayList<>();
        foodImages9.add(new FoodImage("https://bit.ly/2YoJ77H", 9));
        foodImages9.add(new FoodImage("https://bit.ly/2BteuF2", 9));
        foodImages9.add(new FoodImage("https://bit.ly/3fLJf72", 9));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 9));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 9));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 9));


        List<FoodImage> foodImages10 = new ArrayList<>();
        foodImages10.add(new FoodImage("https://bit.ly/2YoJ77H", 10));
        foodImages10.add(new FoodImage("https://bit.ly/2BteuF2", 10));
        foodImages10.add(new FoodImage("https://bit.ly/3fLJf72", 10));
        foodImages.add(new FoodImage("https://bit.ly/2YoJ77H", 10));
        foodImages.add(new FoodImage("https://bit.ly/2BteuF2", 10));
        foodImages.add(new FoodImage("https://bit.ly/3fLJf72", 10));


        foods.add(new Food("Bánh mì", "Bánh mì thịt nướng", 20000, true, 10, 1, 4, 1, foodImages1));
        foods.add(new Food("Nước tăng lực", "Nước tăng lực có gas", 12000, true, 15, 2, 4.2f, 1, foodImages2));
        foods.add(new Food("Cà phê", "Cà phê đen", 15000, true, 20, 3, 4.5f, 1, foodImages3));
        foods.add(new Food("Bánh mì", "Bánh mì thịt nướng", 20000, true, 10, 1, 4, 1, foodImages4));
        foods.add(new Food("Nước tăng lực", "Nước tăng lực có gas", 12000, true, 15, 2, 4.2f, 1, foodImages5));
        foods.add(new Food("Cà phê", "Cà phê đen", 15000, true, 20, 3, 4.5f, 1, foodImages6));
        foods.add(new Food("Bánh mì", "Bánh mì thịt nướng", 20000, true, 10, 1, 4, 1, foodImages7));
        foods.add(new Food("Nước tăng lực", "Nước tăng lực có gas", 12000, true, 15, 2, 4.2f, 1, foodImages8));
        foods.add(new Food("Cà phê", "Cà phê đen", 15000, true, 20, 3, 4.5f, 1, foodImages9));
        foods.add(new Food("Bánh mì", "Bánh mì thịt nướng", 20000, true, 10, 1, 4, 1, foodImages10));

        categories.add(new Category("Đồ ăn", "https://bit.ly/2YoJ77H"));
        categories.add(new Category("Đồ uống", "https://bit.ly/2BteuF2"));
        categories.add(new Category("Đồ ăn", "https://bit.ly/3fLJf72"));

        restaurants.add(new Restaurant("Quán ăn 1", "123 Lê Lợi, P. Bến Thành, Q.1, TP. HCM"));
        restaurants.add(new Restaurant("Quán ăn 2", "123 Lê Lợi, P. Bến Thành, Q.1, TP. HCM"));
        restaurants.add(new Restaurant("Quán ăn 3", "123 Lê Lợi, P. Bến Thành, Q.1, TP. HCM"));
    }

    public void generateData(AppDatabase database) {
        categoryRepository = new CategoryRepository(database);
        foodRepository = new FoodRepository(database);
        restaurantDao = database.restaurantDao();
        foodImageDao = database.foodImageDao();

//        foodImageDao.deleteAll();
//        foodRepository.deleteAllFood();
//        categoryRepository.deleteAllCategory();
//        restaurantDao.deleteAll();

//        restaurantDao.insertAll(restaurants);
//        categoryRepository.insertAllCategories(categories);
//        foodRepository.insertAllFoods(foods);
//        foodImageDao.insertAll(foodImages);
    }
}
