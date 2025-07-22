package com.example.nikeshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nikeshop.R;
import com.example.nikeshop.data.local.entity.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItems;
    private final Context context;
    private final OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onIncrease(Product product);
        void onDecrease(Product product);
    }

    public CartAdapter(Context context, List<Product> cartItems, OnQuantityChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvName, tvPrice, tvSize, tvQty, tvMinus, tvPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvSize = itemView.findViewById(R.id.tvProductSize);
            tvQty = itemView.findViewById(R.id.tvQuantity);
            tvMinus = itemView.findViewById(R.id.btnDecrease);
            tvPlus = itemView.findViewById(R.id.btnIncrease);
        }

        void bind(Product product) {
            tvName.setText(product.getName());
            tvPrice.setText(String.format("$%.2f", product.getPrice()));
            tvSize.setText("Size EU " + product.getSize());
            tvQty.setText(String.valueOf(product.getCartQuantity()));

            Glide.with(context)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.shoes1)
                    .into(ivProductImage);

            tvMinus.setOnClickListener(v -> {
                if (product.getCartQuantity() > 1) {
                    quantityChangeListener.onDecrease(product);
                }
            });

            tvPlus.setOnClickListener(v -> {
                quantityChangeListener.onIncrease(product);
            });
        }

    }

    public void setCartList(List<Product> newCartItems) {
        this.cartItems.clear();
        this.cartItems.addAll(newCartItems);
        notifyDataSetChanged();
    }
}

