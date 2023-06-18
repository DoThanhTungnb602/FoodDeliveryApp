package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "cart_table",
        primaryKeys = {"userId", "foodId"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId", onDelete = ForeignKey.CASCADE)
        })
public class CartTable {
    public int userId;
    public int foodId;
    public int quantity;

    public CartTable(int userId, int foodId, int quantity) {
        this.userId = userId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
