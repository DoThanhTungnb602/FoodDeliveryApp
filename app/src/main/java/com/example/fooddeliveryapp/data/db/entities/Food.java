package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "food")
public class Food {
    @PrimaryKey()
    public int id;
    public String name;
    public String description;
    public int price;
    public boolean availability;
    public int deliveryTime;
    public String categoryId;
    public float averageRating;
    public int restaurantId;

    public Food() {
    }

    @Ignore
    public List<FoodImage> foodImages;

    @Ignore
    public Food(int id, String name, String description, int price, boolean availability, int deliveryTime, String categoryId, float averageRating, int restaurantId, List<FoodImage> foodImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.deliveryTime = deliveryTime;
        this.categoryId = categoryId;
        this.averageRating = averageRating;
        this.restaurantId = restaurantId;
        this.foodImages = foodImages;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<FoodImage> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(List<FoodImage> foodImages) {
        this.foodImages = foodImages;
    }

}
