package com.example.du_an_2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.du_an_2.model.Category;
import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();
}
