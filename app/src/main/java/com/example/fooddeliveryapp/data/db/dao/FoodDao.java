package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * FROM food WHERE id=:id")
    LiveData<Food> getFoodById(int id);

    @Query("SELECT * FROM food WHERE categoryId=:categoryId")
    LiveData<List<Food>> getFoodByCategoryId(int categoryId);

    @Insert
    void insertFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM food")
    void deleteAllFoods();
}
