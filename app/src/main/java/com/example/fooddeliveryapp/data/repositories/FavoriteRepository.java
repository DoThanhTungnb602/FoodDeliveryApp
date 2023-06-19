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

    private final LiveData<List<Favorite>> listFavorite;

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
    public LiveData<List<Favorite>> getFavoriteList() {
        return listFavorite;
    }

    /**
     * Thêm một món ăn vào danh sách yêu thích.
     *
     * @param foodId là id của món ăn.
     */
    public void insertFavorite(int foodId) {
        Favorite favorite = new Favorite(MainActivity.currentUserID, foodId);
        favoriteDao.insertFavorite(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteDao.deleteFavorite(favorite);
    }
}
