package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(int id);

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUsers(List<User> users);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Query("SELECT * FROM user WHERE email=:email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user")
    List<User> getAllUser();
}
