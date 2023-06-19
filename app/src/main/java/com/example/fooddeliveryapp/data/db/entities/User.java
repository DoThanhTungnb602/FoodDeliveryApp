package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String email;
    public String password;
    public String image;
    public String deliveryAddress;

    public User() {
    }

    @Ignore
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Ignore
    public User(String name, String email, String password, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    @Ignore
    public User(String name, String email, String password, String image, String deliveryAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.deliveryAddress = deliveryAddress;
    }
}