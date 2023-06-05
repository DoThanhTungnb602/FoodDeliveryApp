package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String description;
    public String image;
    public double price;
    public int category_id;
    public int restaurantId;
    public float rating;
    public int discount;
    public double discount_price;
    public String status;
}
