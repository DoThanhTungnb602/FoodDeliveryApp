package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Cart;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE userId=:userId")
    LiveData<Cart> getCartByUserId(int userId);

    @Update
    void updateCart(Cart cart);

    @Insert
    void insertCart(Cart cart);

    @Delete
    void deleteCart(Cart cart);
}
