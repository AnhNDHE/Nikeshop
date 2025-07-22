package com.example.nikeshop.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nikeshop.R;
import com.example.nikeshop.adapters.CartAdapter;
import com.example.nikeshop.data.local.entity.Product;
import com.example.nikeshop.ui.ViewModels.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        // Back button
        View topNavBar = findViewById(R.id.top_navbar);
        View btnBack = topNavBar.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, ProductListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // RecyclerView setup
        rvCartItems = findViewById(R.id.rvCartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(this, new ArrayList<>(), new CartAdapter.OnQuantityChangeListener() {
            @Override
            public void onIncrease(Product product) {
                cartViewModel.increaseQuantity(userId, product.getId());
            }

            @Override
            public void onDecrease(Product product) {
                cartViewModel.decreaseQuantity(userId, product.getId());
            }
        });

        rvCartItems.setAdapter(cartAdapter);

        // ViewModel setup
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        // Observe LiveData<List<Product>>
        cartViewModel.getCartWithProducts(userId).observe(this, products -> {
            if (products != null) {
                Log.d("CartActivity", "Số lượng sản phẩm trong giỏ: " + products.size());
                for (Product p : products) {
                    Log.d("CartActivity", "Product: " + p.getName() + " - Qty: " + p.getStockQuantity());
                }
                cartAdapter.setCartList(products);
            } else {
                Log.d("CartActivity", "Không có sản phẩm nào trong giỏ.");
            }
        });


        // Checkout button
        TextView btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        });
    }
}
