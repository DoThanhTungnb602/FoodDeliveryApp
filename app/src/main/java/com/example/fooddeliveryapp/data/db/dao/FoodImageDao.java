package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fooddeliveryapp.data.db.entities.FoodImage;

import java.util.List;

@Dao
public interface FoodImageDao {
    @Query("SELECT * FROM food_image WHERE foodId=:foodId")
    List<FoodImage> getFoodImagesByFoodID(int foodId);

    @Query("DELETE FROM food_image WHERE foodId=:foodId")
    void deleteFoodImageByFoodId(int foodId);

    @Insert
    void insertAll(List<FoodImage> foodImages);

    @Query("DELETE FROM food_image")
    void deleteAll();
}
