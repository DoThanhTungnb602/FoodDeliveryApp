package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart",
        foreignKeys = {
                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId"),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId")
        })
public class Cart {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int foodId;
    public int userId;
    public int quantity;
    public double price;
}
