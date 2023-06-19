package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Query("SELECT * FROM categories WHERE name LIKE :name")
    List<Category> getCategoryByName(String name);

    @Query("SELECT * FROM categories WHERE id=:id")
    Category getCategoryById(int id);

    @Update
    void updateCategory(Category category);

    @Insert
    void insertCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Insert
    void insertAllCategories(List<Category> categories);
}
