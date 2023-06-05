package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String email;
    public String password;
    public String address;
    public String image;
    public String token;
}
