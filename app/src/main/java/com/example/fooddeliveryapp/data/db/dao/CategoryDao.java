package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories WHERE name LIKE :name")
    LiveData<List<Category>> getCategoryByName(String name);

    @Query("SELECT * FROM categories WHERE id=:id")
    LiveData<Category> getCategoryById(int id);

    void updateCategory(Category category);

    @Insert
    void insertCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("DELETE FROM categories")
    void deleteAllCategories();
}
