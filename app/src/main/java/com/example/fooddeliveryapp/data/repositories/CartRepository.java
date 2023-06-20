package com.example.fooddeliveryapp.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.CartDao;
import com.example.fooddeliveryapp.data.db.entities.Cart;

import java.util.List;

public class CartRepository {
    AppDatabase database;
    CartDao cartDao;
    private final LiveData<List<Cart>> cart;

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
    public LiveData<List<Cart>> getAllCart() {
        return cart;
    }

    /**
     * Thêm một món ăn vào giỏ hàng.
     *
     * @param foodId   là id của món ăn.
     * @param quantity là số lượng của món ăn.
     */
    public void insertCart(int foodId, int quantity) {
        Cart cart = new Cart(MainActivity.currentUserID, foodId, quantity);
        cartDao.insertCart(cart);
    }

    public void updateCart(Cart cart) {
        cartDao.updateCart(cart);
    }

    public void deleteCart(Cart cart) {
        cartDao.deleteCart(cart);
    }

    public int isExist(int foodId){return cartDao.isExist(foodId);}

    public Cart getCartByFoodId(int foodId){return cartDao.getCartByFoodId(foodId);}

}
