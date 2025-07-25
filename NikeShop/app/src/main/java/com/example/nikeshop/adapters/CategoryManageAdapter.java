package com.example.nikeshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nikeshop.R;
import com.example.nikeshop.data.local.entity.Category;

import java.util.List;

public class CategoryManageAdapter extends RecyclerView.Adapter<CategoryManageAdapter.CategoryViewHolder> {

    private final Context context;
    private List<Category> categories;
    private final OnCategoryActionListener listener;

    public interface OnCategoryActionListener {
        void onEdit(Category category);
        void onDelete(Category category);
        void onCategoryClick(Category category); // Optional
    }

    public CategoryManageAdapter(Context context, List<Category> categories, OnCategoryActionListener listener) {
        this.context = context;
        this.categories = categories;
        this.listener = listener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false); // ⚠️ Dùng layout đúng
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.tvName.setText(category.getName());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(category));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(category));
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnEdit, btnDelete;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_category_name);
            btnEdit = itemView.findViewById(R.id.btn_edit_category);
            btnDelete = itemView.findViewById(R.id.btn_delete_category);
        }
    }
}
