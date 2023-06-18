package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Restaurant;

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM restaurants WHERE id=:id")
    LiveData<Restaurant> getRestaurantById(int id);

    @Insert
    void insertRestaurant(Restaurant restaurant);

    @Update
    void updateRestaurant(Restaurant restaurant);

    @Delete
    void deleteRestaurant(Restaurant restaurant);
}
