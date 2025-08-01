package com.example.nikeshop.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nikeshop.data.local.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    List<Long> insertAll(List<User> users);
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password_hash = :password LIMIT 1")
    User loginUser(String email, String password);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId")
    LiveData<User> getUserByIdLive(int userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT COUNT(*) FROM users")
    int countUsers();

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);


}
