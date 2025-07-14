package com.example.nikeshop.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.example.nikeshop.data.local.entity.Product;
import com.example.nikeshop.data.repositories.ProductRepository;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nikeshop.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        ImageView btnCart = findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Lấy product_id từ intent
        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId == -1) return;

        ProductRepository productRepository = new ProductRepository(this);
        productRepository.getProductById(productId, product -> runOnUiThread(() -> {
            if (product == null) return;
            // Hiển thị thông tin sản phẩm
            ImageView productImage = findViewById(R.id.product_image);
            TextView productName = findViewById(R.id.product_name);
            TextView productDesc = findViewById(R.id.product_description);
            TextView productPrice = findViewById(R.id.product_price);
            LinearLayout sizeLayout = findViewById(R.id.size_layout);

            // Hiển thị ảnh sản phẩm (nếu dùng resource name)
            if (productImage != null && product.getImageUrl() != null) {
                int resId = getResources().getIdentifier(product.getImageUrl(), "drawable", getPackageName());
                if (resId != 0) productImage.setImageResource(resId);
            }
            if (productName != null) productName.setText(product.getName());
            if (productDesc != null) productDesc.setText(product.getDescription());
            if (productPrice != null) productPrice.setText("$" + product.getPrice());

            // Hiển thị các size
            if (sizeLayout != null && product.getSizes() != null) {
                sizeLayout.removeAllViews();
                for (String size : product.getSizes()) {
                    android.util.Log.d("Size", size);
                    TextView tv = new TextView(this);
                    tv.setText("EU " + size);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) getResources().getDisplayMetrics().density * 80, // 80dp width
                        (int) getResources().getDisplayMetrics().density * 48  // 48dp height
                    );
                    params.setMarginEnd((int) (getResources().getDisplayMetrics().density * 16)); // 16dp margin end
                    tv.setLayoutParams(params);
                    tv.setBackgroundResource(R.drawable.size_unselected_bg);
                    tv.setTextColor(getResources().getColor(android.R.color.black));
                    tv.setTextSize(16);
                    tv.setPadding(0, 0, 0, 0);
                    tv.setGravity(android.view.Gravity.CENTER);
                    tv.setTypeface(null, android.graphics.Typeface.BOLD);
                    sizeLayout.addView(tv);
                }
            }
        }));
    }
}