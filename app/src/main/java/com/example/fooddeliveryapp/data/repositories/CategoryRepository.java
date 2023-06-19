package com.example.fooddeliveryapp.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.CategoryDao;
import com.example.fooddeliveryapp.data.db.entities.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CategoryRepository {
    AppDatabase database;

    CategoryDao categoryDao;

    private final List<Category> listCategory;
    String categoryUrl = "https://www.themealdb.com/api/json/v1/1/categories.php";

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
     * Lấy danh sách các loại món ăn từ server.
     *
     * @return danh sách các loại món ăn.
     */
    public CompletableFuture<List<Category>> getCategoryFromServer() {
        OkHttpClient client = new OkHttpClient();
        CompletableFuture<List<Category>> future = new CompletableFuture<>();
        Request request = new Request.Builder().url(categoryUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull java.io.IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws java.io.IOException {
                try {
                    assert response.body() != null;
                    String json = response.body().string();
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("categories");
                    List<Category> categories = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        int categoryID = Integer.parseInt(jsonArray.getJSONObject(i).getString("idCategory"));
                        String categoryName = jsonArray.getJSONObject(i).getString("strCategory");
                        String categoryImageUrl = jsonArray.getJSONObject(i).getString("strCategoryThumb");
                        Category category = new Category(categoryID, categoryName, categoryImageUrl);
                        categories.add(category);
                    }
                    future.complete(categories);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return future;
    }

    /**
     * Thêm một loại món ăn vào danh sách.
     */
    public void insertCategory(Category category) {
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
