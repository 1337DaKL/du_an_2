package com.example.du_an_2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.du_an_2.database.AppDatabase;
import com.example.du_an_2.model.Order;
import com.example.du_an_2.model.OrderDetail;
import com.example.du_an_2.model.Product;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ListView lvOrderDetails;
    private TextView tvTotalAmount;
    private Button btnCheckout;
    private int orderId;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvOrderDetails = findViewById(R.id.lvOrderDetails);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnCheckout = findViewById(R.id.btnCheckout);

        orderId = getIntent().getIntExtra("ORDER_ID", -1);
        loadOrderDetails();

        btnCheckout.setOnClickListener(v -> {
            if (order != null) {
                order.status = "Paid";
                AppDatabase.getInstance(this).orderDao().update(order);
                Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void loadOrderDetails() {
        AppDatabase db = AppDatabase.getInstance(this);
        order = db.orderDao().getOrderById(orderId);
        if (order != null) {
            tvTotalAmount.setText("Total: $" + order.totalAmount);
            List<OrderDetail> details = db.orderDetailDao().getOrderDetailsByOrder(orderId);
            List<String> displayList = new ArrayList<>();
            for (OrderDetail d : details) {
                Product p = db.productDao().getProductById(d.productId);
                String name = (p != null) ? p.name : "Unknown Product";
                displayList.add(name + " x " + d.quantity + " - $" + (d.quantity * d.unitPrice));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
            lvOrderDetails.setAdapter(adapter);
        }
    }
}
