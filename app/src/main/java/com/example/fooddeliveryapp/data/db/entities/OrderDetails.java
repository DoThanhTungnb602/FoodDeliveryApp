package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "order_details",
        primaryKeys = {"orderId", "foodId"},
        foreignKeys = {
                @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId", onDelete = ForeignKey.CASCADE)
        })
public class OrderDetails {
    public int orderId;
    public int foodId;
    public int quantity;

    public OrderDetails(int orderId, int foodId, int quantity) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
    }
}
