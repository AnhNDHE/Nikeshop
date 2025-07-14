package com.example.nikeshop.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nikeshop.data.local.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    // Thêm mới sản phẩm
    @Insert
    long insertProduct(Product product);

    // Lấy tất cả sản phẩm
    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    // Lấy sản phẩm theo id
    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    Product getProductById(int id);

    // Xóa sản phẩm theo id
    @Query("DELETE FROM products WHERE id = :id")
    void deleteProductById(int id);

    // Cập nhật sản phẩm
    @Update
    void updateProduct(Product product);
}

