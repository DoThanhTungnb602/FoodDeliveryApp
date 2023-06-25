package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders WHERE userId=:userId")
    List<Order> getOrderListByUserID(int userId);

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM orders WHERE orderDate=:date")
    Order getOrderByDate(String date);
}
