package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String address;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
