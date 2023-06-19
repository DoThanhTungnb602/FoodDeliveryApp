package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "food"
//        foreignKeys = {
//                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE),
//                @ForeignKey(entity = Restaurant.class, parentColumns = "id", childColumns = "restaurantId", onDelete = ForeignKey.CASCADE)
//        }
)
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

    public Food() {
    }

    @Ignore
    public List<FoodImage> foodImages;

    @Ignore
    public Food(String name, String description, double price, boolean availability, int deliveryTime, int categoryId, float averageRating, int restaurantId, List<FoodImage> foodImages) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
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
