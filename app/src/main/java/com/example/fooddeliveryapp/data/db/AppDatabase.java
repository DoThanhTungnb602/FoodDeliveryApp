package com.example.fooddeliveryapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fooddeliveryapp.data.db.dao.CartDao;
import com.example.fooddeliveryapp.data.db.dao.CategoryDao;
import com.example.fooddeliveryapp.data.db.dao.FavoriteDao;
import com.example.fooddeliveryapp.data.db.dao.FoodDao;
import com.example.fooddeliveryapp.data.db.dao.FoodImageDao;
import com.example.fooddeliveryapp.data.db.dao.OrderDao;
import com.example.fooddeliveryapp.data.db.dao.OrderDetailsDao;
import com.example.fooddeliveryapp.data.db.dao.PaymentMethodDao;
import com.example.fooddeliveryapp.data.db.dao.RestaurantDao;
import com.example.fooddeliveryapp.data.db.dao.UserDao;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.data.db.entities.Category;
import com.example.fooddeliveryapp.data.db.entities.Favorite;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.FoodImage;
import com.example.fooddeliveryapp.data.db.entities.Order;
import com.example.fooddeliveryapp.data.db.entities.OrderDetails;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.Restaurant;
import com.example.fooddeliveryapp.data.db.entities.User;

@Database(entities = {
        Cart.class,
        Category.class,
        Favorite.class,
        Food.class,
        FoodImage.class,
        Order.class,
        OrderDetails.class,
        PaymentMethod.class,
        Restaurant.class,
        User.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao cartDao();

    public abstract CategoryDao categoryDao();

    public abstract FavoriteDao favoriteDao();

    public abstract FoodDao foodDao();

    public abstract FoodImageDao foodImageDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailsDao orderDetailsDao();

    public abstract PaymentMethodDao paymentMethodDao();

    public abstract RestaurantDao restaurantDao();

    public abstract UserDao userDao();

    private static volatile AppDatabase appDatabase;

    public static AppDatabase getDatabase(final Context context) {
        if (appDatabase == null) synchronized (AppDatabase.class) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "food_delivery.db").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
