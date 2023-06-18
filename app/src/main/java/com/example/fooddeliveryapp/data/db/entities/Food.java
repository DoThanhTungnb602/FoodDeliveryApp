package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "food",
        foreignKeys = {
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Restaurant.class, parentColumns = "id", childColumns = "restaurantId", onDelete = ForeignKey.CASCADE)
        })
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public double price;
    public boolean availability;
    public int deliveryTime;
    public int categoryId;
    public float averageRating;
    public int restaurantId;
}
