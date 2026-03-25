package com.example.du_an_2.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryId",
                onDelete = ForeignKey.CASCADE))
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int categoryId;
    public String name;
    public double price;
    public String description;

    public Product(int categoryId, String name, double price, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
