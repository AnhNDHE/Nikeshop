package com.example.nikeshop.data.local.dao;

import androidx.room.Dao;

@Dao
public interface CategoryDao {
    // Thêm mới category
    @androidx.room.Insert
    long insertCategory(com.example.nikeshop.data.local.entity.Category category);

    // Lấy tất cả category
    @androidx.room.Query("SELECT * FROM categories")
    java.util.List<com.example.nikeshop.data.local.entity.Category> getAllCategories();

    // Xóa category theo id
    @androidx.room.Query("DELETE FROM categories WHERE id = :id")
    void deleteCategoryById(int id);

    // Cập nhật category
    @androidx.room.Update
    void updateCategory(com.example.nikeshop.data.local.entity.Category category);
}
