package com.example.du_an_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.du_an_2.database.AppDatabase;
import com.example.du_an_2.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private ListView lvProducts;
    private List<Product> productList;
    private int categoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        lvProducts = findViewById(R.id.lvProducts);
        categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        loadProducts();

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productList.get(position);
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.id);
            startActivity(intent);
        });
    }

    private void loadProducts() {
        AppDatabase db = AppDatabase.getInstance(this);
        if (categoryId != -1) {
            productList = db.productDao().getProductsByCategory(categoryId);
        } else {
            productList = db.productDao().getAllProducts();
        }

        List<String> displayList = new ArrayList<>();
        for (Product p : productList) {
            displayList.add(p.name + " - $" + p.price);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvProducts.setAdapter(adapter);
    }
}
