package com.example.fooddeliveryapp.data.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.CartDao;
import com.example.fooddeliveryapp.data.db.entities.Cart;

import java.util.List;

public class CartRepository {
    AppDatabase database;
    CartDao cartDao;

    /**
     * Khởi tạo đối tượng CartRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public CartRepository(AppDatabase database) {
        this.database = database;
        this.cartDao = database.cartDao();
    }

    /**
     * Lấy danh sách các món ăn trong giỏ hàng.
     *
     * @return danh sách các món ăn trong giỏ hàng.
     */
    public List<Cart> getAllCart() {
        return cartDao.getAllCartByUserID(MainActivity.currentUserID);
    }

    /**
     * Thêm một món ăn vào giỏ hàng.
     *
     * @param foodId   là id của món ăn.
     * @param quantity là số lượng của món ăn.
     */
    public void insertCart(Cart cart) {
        cartDao.insertCart(cart);
    }

    public void updateCart(Cart cart) {
        cartDao.updateCart(cart);
    }

    public void deleteCart(Cart cart) {
        cartDao.deleteCart(cart);
    }

    public void deleteAllCart() {
        cartDao.deleteAllCart();
    }
    public int isExist(int foodId, int userId){return cartDao.isExist(foodId, userId);}

    public Cart getCartByFoodId(int foodId){return cartDao.getCartByFoodId(foodId);}

}
