package com.example.fooddeliveryapp.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodDao;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;

import java.util.List;
import java.util.Objects;

public class FoodRepository {
    AppDatabase database;
    FoodDao foodDao;
    FoodImageDao foodImageDao;
    private final List<Food> listFood;

    /**
     * Khởi tạo đối tượng FoodRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public FoodRepository(AppDatabase database) {
        this.database = database;
        this.foodDao = database.foodDao();
        this.foodImageDao = database.foodImageDao();
        this.listFood = foodDao.getAllFoods();
    }

    /**
     * Lấy danh sách các món ăn.
     *
     * @return danh sách các món ăn.
     */
    public List<Food> getAllFood() {
        for (Food food : listFood) {
            food.setFoodImages(foodImageDao.getFoodImagesByFoodID(food.getId()));
        }
        return listFood;
    }

    /**
     * Lấy ra món ăn theo tên. Dùng phương thức getFoodImages để lấy ra List ảnh của món ăn.
     *
     * @param id là id của món ăn.
     * @return món ăn theo id.
     */
    public Food getFoodById(int id) {
        Food food = foodDao.getFoodById(id);
        food.setFoodImages(foodImageDao.getFoodImagesByFoodID(food.getId()));
        return foodDao.getFoodById(id);
    }

    /**
     * Lấy danh sách các món ăn theo category.
     *
     * @param categoryId là id của category.
     * @return danh sách các món ăn theo category.
     */
    public List<Food> getFoodByCategoryId(int categoryId) {
        return foodDao.getFoodByCategoryId(categoryId);
    }

    /**
     * Thêm một món ăn vào danh sách.
     *
     * @param name          là tên của món ăn.
     * @param description   là mô tả của món ăn.
     * @param price         là giá của món ăn.
     * @param availability  là trạng thái của món ăn.
     * @param deliveryTime  là thời gian giao hàng của món ăn.
     * @param categoryId    là id của category.
     * @param averageRating là rating trung bình của món ăn.
     * @param restaurantId  là id của nhà hàng.
     * @param foodImages    là danh sách các hình ảnh của món ăn.
     */
    public void insertFood(String name, String description, double price, boolean availability, int deliveryTime, int categoryId, float averageRating, int restaurantId, List<FoodImage> foodImages) {
        Food food = new Food(name, description, price, availability, deliveryTime, categoryId, averageRating, restaurantId, foodImages);
        foodDao.insertFood(food);
    }

    public void updateFood(Food food) {
        foodDao.updateFood(food);
    }

    public void deleteAllFood() {
        foodDao.deleteAllFoods();
    }

    public void deleteFood(Food food) {
        foodDao.deleteFood(food);
    }

    public void insertAllFoods(List<Food> foods) {
        foodDao.insertAllFoods(foods);
    }
}
