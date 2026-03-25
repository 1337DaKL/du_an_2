package com.example.du_an_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.du_an_2.database.AppDatabase;
import com.example.du_an_2.model.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    private ListView lvCategories;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        lvCategories = findViewById(R.id.lvCategories);
        loadCategories();

        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            Category category = categoryList.get(position);
            Intent intent = new Intent(CategoryListActivity.this, ProductListActivity.class);
            intent.putExtra("CATEGORY_ID", category.id);
            startActivity(intent);
        });
    }

    private void loadCategories() {
        categoryList = AppDatabase.getInstance(this).categoryDao().getAllCategories();
        List<String> displayList = new ArrayList<>();
        for (Category c : categoryList) {
            displayList.add(c.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvCategories.setAdapter(adapter);
    }
}
