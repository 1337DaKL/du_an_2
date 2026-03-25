package com.example.du_an_2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.du_an_2.model.User;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);
    
    @Query("SELECT COUNT(*) FROM users")
    int count();
}
