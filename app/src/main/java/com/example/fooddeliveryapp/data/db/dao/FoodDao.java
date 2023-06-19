package com.example.fooddeliveryapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food")
    List<Food> getAllFoods();

    @Query("SELECT * FROM food WHERE id=:id")
    Food getFoodById(int id);

    @Query("SELECT * FROM food WHERE categoryName=:categoryName")
    List<Food> getFoodByCategoryName(String categoryName);

    @Insert
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM food")
    void deleteAllFoods();

    @Insert
    void insertAllFoods(List<Food> foods);
}
