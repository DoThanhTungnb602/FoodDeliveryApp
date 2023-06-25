package com.example.fooddeliveryapp.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.FavoriteDao;
import com.example.fooddeliveryapp.data.db.entities.Favorite;

import java.util.List;

public class FavoriteRepository {
    AppDatabase database;

    FavoriteDao favoriteDao;

    private final List<Favorite> listFavorite;

    /**
     * Khởi tạo đối tượng FavoriteRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public FavoriteRepository(AppDatabase database) {
        this.database = database;
        this.favoriteDao = database.favoriteDao();
        this.listFavorite = favoriteDao.getFavoriteListByUserID(MainActivity.currentUserID);
    }

    /**
     * Lấy danh sách các món ăn yêu thích.
     *
     * @return danh sách các món ăn yêu thích.
     */
    public List<Favorite> getFavoriteList() {
        return listFavorite;
    }


    /**
     * Thêm một món ăn vào danh sách yêu thích.
     *
     * @param foodId là id của món ăn.
     */
    public void insertFavorite(Favorite favorite) {
        favoriteDao.insertFavorite(favorite);
        System.out.println(favorite.foodId);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteDao.deleteFavorite(favorite);
    }

    public int isExist(int foodId){return favoriteDao.isExist(foodId);}
}
