package com.example.fooddeliveryapp;


import android.content.Context;

import androidx.annotation.NonNull;


import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;

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
    AppDatabase db;
    FoodImageDao foodImageDao;
    String randomFoodUrl = "https://www.themealdb.com/api/json/v1/1/random.php";

    public CompletableFuture<Food> getFoodFromServer(Context Context) {
        db = AppDatabase.getDatabase(Context);
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
                    foodImageDao = db.foodImageDao();
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImages.add(new FoodImage(foodImageUrl, foodID));
                    foodImageDao.insertAll(foodImages);
                    List<FoodImage> foodImagesFromDB = foodImageDao.getAllFoodImages();
                    Food food = new Food(foodID, foodName, foodDescription, foodPrice, foodAvailability, foodDeliveryTime, foodCategory, foodAverageRating, foodRestaurantId, foodImages);
                    future.complete(food);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return future;
    }

}
