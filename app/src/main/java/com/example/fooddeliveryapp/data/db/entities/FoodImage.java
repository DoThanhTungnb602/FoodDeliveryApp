package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_image"
//        foreignKeys = {
//                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId", onDelete = ForeignKey.CASCADE)
//        }
)
public class FoodImage {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String imageUrl;
    public int foodId;

    public FoodImage(String imageUrl, int foodId) {
        this.imageUrl = imageUrl;
        this.foodId = foodId;
    }
}
