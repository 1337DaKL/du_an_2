package com.example.du_an_2.database;

import android.content.Context;
import com.example.du_an_2.model.Category;
import com.example.du_an_2.model.Product;
import com.example.du_an_2.model.User;

public class DataInitializer {
    public static void initData(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        if (db.userDao().count() == 0) {
            // Add Users
            db.userDao().insert(new User("admin", "admin123", "Admin User", "admin@example.com"));
            db.userDao().insert(new User("user1", "pass123", "Regular User", "user1@example.com"));

            // Add Categories
            db.categoryDao().insert(new Category("Electronics", "Gadgets and devices"));
            db.categoryDao().insert(new Category("Clothing", "Apparel and accessories"));
            db.categoryDao().insert(new Category("Home & Garden", "Items for home and garden"));

            // Add Products
            db.productDao().insert(new Product(1, "Smartphone", 500.0, "Latest model smartphone"));
            db.productDao().insert(new Product(1, "Laptop", 1200.0, "High performance laptop"));
            db.productDao().insert(new Product(2, "T-Shirt", 20.0, "Cotton t-shirt"));
            db.productDao().insert(new Product(2, "Jeans", 50.0, "Blue denim jeans"));
            db.productDao().insert(new Product(3, "Coffee Maker", 80.0, "Automatic coffee maker"));
        }
    }
}
