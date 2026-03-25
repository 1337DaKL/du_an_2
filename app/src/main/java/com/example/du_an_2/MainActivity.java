package com.example.du_an_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.du_an_2.database.AppDatabase;
import com.example.du_an_2.database.DataInitializer;
import com.example.du_an_2.model.User;
import com.example.du_an_2.utils.AuthManager;

public class MainActivity extends AppCompatActivity {

    private AuthManager authManager;
    private TextView tvWelcome;
    private Button btnLogin, btnLogout, btnProducts, btnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Data
        DataInitializer.initData(this);

        authManager = new AuthManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);
        btnProducts = findViewById(R.id.btnProducts);
        btnCategories = findViewById(R.id.btnCategories);

        btnProducts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProductListActivity.class));
        });

        btnCategories.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CategoryListActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            authManager.logout();
            updateUI();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (authManager.isLoggedIn()) {
            User user = AppDatabase.getInstance(this).userDao().getUserById(authManager.getUserId());
            if (user != null) {
                tvWelcome.setText("Welcome, " + user.fullName + "!");
            }
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setText("Not logged in");
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
    }
}
