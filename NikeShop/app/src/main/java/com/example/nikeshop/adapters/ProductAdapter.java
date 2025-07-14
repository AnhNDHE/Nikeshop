package com.example.nikeshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nikeshop.R;
import com.example.nikeshop.data.local.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products = new ArrayList<>();
    private final Context context;
    private OnProductClickListener listener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txtName.setText(product.getName());
        holder.txtDesc.setText(product.getDescription());
        holder.txtPrice.setText("$" + product.getPrice());
        // Set ảnh mặc định nếu imageUrl rỗng hoặc null
        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            holder.imgProduct.setImageResource(R.drawable.shoes1);
        } else {
            // TODO: Load image từ URL nếu có (Glide/Picasso)
            holder.imgProduct.setImageResource(R.drawable.shoes1);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc, txtPrice;
        ImageView imgProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_product_name);
            txtDesc = itemView.findViewById(R.id.txt_product_desc);
            txtPrice = itemView.findViewById(R.id.txt_product_price);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}
