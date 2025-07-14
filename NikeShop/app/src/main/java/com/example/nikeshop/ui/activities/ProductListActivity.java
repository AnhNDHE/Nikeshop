package com.example.nikeshop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nikeshop.R;
import com.example.nikeshop.adapters.ProductAdapter;
import com.example.nikeshop.data.repositories.CategoryRepository;
import com.example.nikeshop.data.repositories.ProductRepository;
import com.example.nikeshop.data.local.entity.Category;
import com.example.nikeshop.data.local.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BottomMenuActivity {
    private ProductAdapter productAdapter;
    private ProductRepository productRepository;
    private RecyclerView recyclerView;
    private int categoryId;
    private TextView tvTitle;
    private CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        // Product Card Clicks



        ImageView btnCart = findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        tvTitle = findViewById(R.id.tv_title);
        // Lấy tên category từ database
        categoryRepository = new CategoryRepository(this);
        categoryRepository.getAllCategories(categories -> runOnUiThread(() -> {
            for (Category c : categories) {
                if (c.getId() == categoryId) {
                    tvTitle.setText(c.getName());
                    break;
                }
            }
        }));

        recyclerView = findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("category_id", -1);
        productRepository = new ProductRepository(this);
        productRepository.getAllProducts(products -> runOnUiThread(() -> {
            List<Product> filtered = new ArrayList<>();
            for (Product p : products) {
                if (p.getCategoryId() == categoryId) filtered.add(p);
            }
            productAdapter.setProducts(filtered);
        }));

        setupBottomMenu(R.id.nav_home);
    }
}