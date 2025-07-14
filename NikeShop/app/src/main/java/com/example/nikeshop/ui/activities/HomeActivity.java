package com.example.nikeshop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nikeshop.R;
import com.example.nikeshop.adapters.CategoryAdapter;
import com.example.nikeshop.adapters.ProductAdapter;
import com.example.nikeshop.data.local.entity.Category;
import com.example.nikeshop.data.repositories.CategoryRepository;
import com.example.nikeshop.data.repositories.ProductRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BottomMenuActivity {
    private ProductAdapter productAdapter;
    private ProductRepository productRepository;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private CategoryRepository categoryRepository;
    private RecyclerView recyclerViewCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupBottomMenu(R.id.nav_home);

        recyclerView = findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        productAdapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
            // Có thể truyền id sản phẩm qua intent nếu cần
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });

        productRepository = new ProductRepository(this);
        productRepository.getAllProducts(products -> runOnUiThread(() -> {
            productAdapter.setProducts(products);
        }));

        recyclerViewCategories = findViewById(R.id.recycler_view_categories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(this);
        recyclerViewCategories.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryClickListener(category -> {
            Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
            intent.putExtra("category_id", category.getId());
            startActivity(intent);
        });
        categoryRepository = new CategoryRepository(this);
        categoryRepository.getAllCategories(categories -> runOnUiThread(() -> {
            categoryAdapter.setCategories(categories);
        }));

        ImageView btnCart = findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        }
    }
