package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.CartTable;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart_table WHERE userId=:userId")
    LiveData<List<CartTable>> getAllCartByUserID(int userId);

    @Insert
    void insertCart(CartTable cart);

    @Update
    void updateCart(CartTable cart);

    @Delete
    void deleteCart(CartTable cart);

    @Query("SELECT COUNT(foodId) FROM cart_table WHERE foodId = :foodId")
    int isExist(int foodId);

    @Query("SELECT * FROM cart_table WHERE foodId = :foodId")
    CartTable getCartByFoodId(int foodId);
}
