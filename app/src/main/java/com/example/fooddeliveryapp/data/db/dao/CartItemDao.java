package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {
    @Query("SELECT * FROM cart_items WHERE cartId=:cartId")
    LiveData<List<CartItem>> getCartItemByCartId(int cartId);

    @Insert
    void insertCartItem(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);
}
