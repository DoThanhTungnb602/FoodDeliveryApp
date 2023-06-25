package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Cart;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE userId=:userId")
    List<Cart> getAllCartByUserID(int userId);

    @Insert
    void insertCart(Cart cart);

    @Update
    void updateCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);

    @Query("DELETE FROM cart")
    void deleteAllCart();
    @Query("SELECT COUNT(foodId) FROM cart WHERE foodId = :foodId")
    int isExist(int foodId);

    @Query("SELECT * FROM cart WHERE foodId = :foodId")
    Cart getCartByFoodId(int foodId);

}
