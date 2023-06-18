package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "cart_items",
        primaryKeys = {"cartId", "foodId"},
        foreignKeys = {
                @ForeignKey(entity = Cart.class, parentColumns = "id", childColumns = "cartId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId", onDelete = ForeignKey.CASCADE)
        })
public class CartItem {
    public int cartId;
    public int foodId;
    public int quantity;
}
