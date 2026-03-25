package com.example.du_an_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.du_an_2.database.AppDatabase;
import com.example.du_an_2.model.Order;
import com.example.du_an_2.model.OrderDetail;
import com.example.du_an_2.model.Product;
import com.example.du_an_2.utils.AuthManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvName, tvPrice, tvDescription;
    private Button btnAddToCart;
    private int productId;
    private Product product;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        authManager = new AuthManager(this);
        tvName = findViewById(R.id.tvDetailName);
        tvPrice = findViewById(R.id.tvDetailPrice);
        tvDescription = findViewById(R.id.tvDetailDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        loadProduct();

        btnAddToCart.setOnClickListener(v -> {
            if (!authManager.isLoggedIn()) {
                Toast.makeText(this, "Please login to add to cart", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                addToCart();
            }
        });
    }

    private void loadProduct() {
        product = AppDatabase.getInstance(this).productDao().getProductById(productId);
        if (product != null) {
            tvName.setText(product.name);
            tvPrice.setText("$" + product.price);
            tvDescription.setText(product.description);
        }
    }

    private void addToCart() {
        AppDatabase db = AppDatabase.getInstance(this);
        int userId = authManager.getUserId();

        // 1. Get or Create Pending Order
        Order order = db.orderDao().getPendingOrderByUser(userId);
        long orderId;
        if (order == null) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            order = new Order(userId, date, "Pending", 0.0);
            orderId = db.orderDao().insert(order);
        } else {
            orderId = order.id;
        }

        // 2. Add OrderDetail
        OrderDetail detail = new OrderDetail((int) orderId, productId, 1, product.price);
        db.orderDetailDao().insert(detail);

        // 3. Update Order Total (optional but good)
        order = db.orderDao().getOrderById((int) orderId);
        order.totalAmount += product.price;
        db.orderDao().update(order);

        // 4. Ask to continue or checkout
        new AlertDialog.Builder(this)
                .setTitle("Added to Cart")
                .setMessage("Do you want to continue shopping or checkout?")
                .setPositiveButton("Checkout", (dialog, which) -> {
                    Intent intent = new Intent(ProductDetailActivity.this, OrderActivity.class);
                    intent.putExtra("ORDER_ID", (int) orderId);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Continue", (dialog, which) -> {
                    finish();
                })
                .show();
    }
}
