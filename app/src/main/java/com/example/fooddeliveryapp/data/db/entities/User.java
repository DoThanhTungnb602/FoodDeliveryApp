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
    public User(String name, String email, String password, String deliveryAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.deliveryAddress = deliveryAddress;
    }
    @Ignore
    public User(String name, String email, String password, String image, String deliveryAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.deliveryAddress = deliveryAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}