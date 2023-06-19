package com.example.fooddeliveryapp.data.repositories;

import androidx.annotation.NonNull;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodDao;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.dao.RestaurantDao;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FoodRepository {
    AppDatabase database;
    FoodDao foodDao;
    FoodImageDao foodImageDao;
    RestaurantDao restaurantDao;
    private final List<Food> listFood;

    String randomFoodUrl = "https://www.themealdb.com/api/json/v1/1/random.php";

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
        this.restaurantDao = database.restaurantDao();
    }

    /**
     * Lấy danh sách các món ăn.
     *
     * @return danh sách các món ăn.
     */
    public List<Food> getAllFood() {
        for (Food food : listFood) {
            List<FoodImage> foodImages = foodImageDao.getFoodImagesByFoodID(food.getId());
            food.setFoodImages(foodImages);
        }
        return listFood;
    }

    /**
     * Lấy đối tượng cửa hàng theo món ăn.
     *
     * @param food Truyền vào một đối tượng món ăn.
     * @return Đối tượng cửa hàng.
     */
    public Restaurant getRestaurant(Food food) {
        return restaurantDao.getRestaurantById(food.getRestaurantId());
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
    public List<Food> getFoodByCategoryName(String categoryName) {
        return foodDao.getFoodByCategoryName(categoryName);
    }

    /**
     * Lấy random món ăn từ server
     *
     * @return Trả về một đối tượng Food ngẫu nhiên từ server
     */
    public CompletableFuture<Food> getFoodFromServer() {
        OkHttpClient client = new OkHttpClient();
        CompletableFuture<Food> future = new CompletableFuture<>();
        Request request = new Request.Builder().url(randomFoodUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull java.io.IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws java.io.IOException {
                try {
                    assert response.body() != null;
                    String json = response.body().string();
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("meals");

                    int foodID = Integer.parseInt(jsonArray.getJSONObject(0).getString("idMeal"));
                    String foodName = jsonArray.getJSONObject(0).getString("strMeal");
                    String foodDescription = jsonArray.getJSONObject(0).getString("strInstructions");
                    int foodPrice = (int) ((Math.random() * 50) + 2) * 10000;
                    boolean foodAvailability = true;
                    int foodDeliveryTime = (int) ((Math.random() * 30) + 10);
                    String foodCategory = jsonArray.getJSONObject(0).getString("strCategory");
                    float foodAverageRating = (float) Math.round(((Math.random() * 5) + 2) * 10) / 10;
                    int foodRestaurantId = (int) ((Math.random() * 5) + 1);
                    String foodImageUrl = jsonArray.getJSONObject(0).getString("strMealThumb");

                    List<FoodImage> foodImages = new ArrayList<>();
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImageDao.insertAll(foodImages);

                    Food food = new Food(foodID, foodName, foodDescription, foodPrice, foodAvailability, foodDeliveryTime, foodCategory, foodAverageRating, foodRestaurantId, foodImages);
                    future.complete(food);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return future;
    }

    /**
     * Thêm một món ăn vào danh sách.
     */
    public void insertFood(Food food) {
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
    public List<Food> searchFood(String name){
        return foodDao.searchFood(name);
    }
}
