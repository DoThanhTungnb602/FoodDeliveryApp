package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "favorites",
        primaryKeys = {"foodId", "userId"},
        foreignKeys = {
                @ForeignKey(entity = Food.class, parentColumns = "id", childColumns = "foodId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        })
public class Favorite {
    public int foodId;
    public int userId;
}
