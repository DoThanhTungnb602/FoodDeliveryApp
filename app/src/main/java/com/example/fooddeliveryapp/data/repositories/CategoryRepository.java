package com.example.fooddeliveryapp.data.repositories;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.CategoryDao;
import com.example.fooddeliveryapp.data.db.entities.Category;

import java.util.List;

public class CategoryRepository {
    AppDatabase database;

    CategoryDao categoryDao;

    private final List<Category> listCategory;

    /**
     * Khởi tạo đối tượng CategoryRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public CategoryRepository(AppDatabase database) {
        this.database = database;
        this.categoryDao = database.categoryDao();
        this.listCategory = categoryDao.getAllCategories();
    }

    /**
     * Lấy danh sách các loại món ăn.
     *
     * @return danh sách các loại món ăn.
     */
    public List<Category> getAllCategory() {
        return listCategory;
    }

    /**
     * Lấy danh sách các loại món ăn theo tên.
     *
     * @param name là tên của loại món ăn.
     * @return danh sách các loại món ăn theo tên.
     */
    public List<Category> getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }

    /**
     * Lấy loại món ăn theo id.
     *
     * @param id là id của loại món ăn.
     * @return loại món ăn theo id.
     */
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    /**
     * Thêm một loại món ăn vào danh sách.
     *
     * @param name  là tên của loại món ăn.
     * @param image là ảnh của loại món ăn.
     */
    public void insertCategory(String name, String image) {
        Category category = new Category(name, image);
        categoryDao.insertCategory(category);
    }

    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    public void deleteAllCategory() {
        categoryDao.deleteAllCategories();
    }

    public void deleteCategory(Category category) {
        categoryDao.deleteCategory(category);
    }

    public void insertAllCategories(List<Category> categories) {
        categoryDao.insertAllCategories(categories);
    }


}
