package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.OrderDetails;

import java.util.List;

@Dao
public interface OrderDetailsDao {
    @Query("SELECT * FROM order_details WHERE orderId=:orderId")
    List<OrderDetails> getOrderDetailsByOrderId(int orderId);

    @Insert
    void insertOrderDetails(OrderDetails orderDetails);

    @Update
    void updateOrderDetails(OrderDetails orderDetails);

    @Query("DELETE FROM order_details WHERE orderId=:orderId")
    void deleteOrderDetailsByOrderId(int orderId);
}
