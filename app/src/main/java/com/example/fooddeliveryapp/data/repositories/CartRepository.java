package com.example.fooddeliveryapp.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.CartDao;
import com.example.fooddeliveryapp.data.db.entities.CartTable;

import java.util.List;

public class CartRepository {
    AppDatabase database;
    CartDao cartDao;
    private final LiveData<List<CartTable>> cart;

    /**
     * Khởi tạo đối tượng CartRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public CartRepository(AppDatabase database) {
        this.database = database;
        this.cartDao = database.cartDao();
        this.cart = cartDao.getAllCartByUserID(MainActivity.currentUserID);
    }

    /**
     * Lấy danh sách các món ăn trong giỏ hàng.
     *
     * @return danh sách các món ăn trong giỏ hàng.
     */
    public LiveData<List<CartTable>> getAllCart() {
        return cart;
    }

    /**
     * Thêm một món ăn vào giỏ hàng.
     *
     * @param foodId   là id của món ăn.
     * @param quantity là số lượng của món ăn.
     */
    public void insertCart(int foodId, int quantity) {
        CartTable cart = new CartTable(MainActivity.currentUserID, foodId, quantity);
        cartDao.insertCart(cart);
    }

    public void updateCart(CartTable cart) {
        cartDao.updateCart(cart);
    }

    public void deleteCart(CartTable cart) {
        cartDao.deleteCart(cart);
    }
}
