package com.example.nikeshop.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nikeshop.data.local.entity.Cart;
import com.example.nikeshop.data.local.entity.Product;

import java.util.Date;
import java.util.List;

@Dao
public interface CartDao {

    // === INSERT ===

    @Insert
    long insert(Cart cart);

    @Insert
    List<Long> insertAll(List<Cart> carts);


    // === QUERY ===
    @Query("SELECT * FROM carts WHERE user_id = :userId AND product_id = :productId AND deleted_at IS NULL LIMIT 1")
    Cart getCartItem(int userId, int productId);

    @Query("SELECT p.*, c.quantity as stockQuantity " +
            "FROM products p " +
            "INNER JOIN carts c ON p.id = c.product_id " +
            "WHERE c.user_id = :userId AND c.deleted_at IS NULL")
    LiveData<List<Product>> getCartByUser(int userId);

    @Query("SELECT p.*, c.quantity AS cartQuantity " +
            "FROM products p " +
            "INNER JOIN carts c ON p.id = c.product_id " +
            "WHERE c.user_id = :userId AND c.deleted_at IS NULL")
    LiveData<List<Product>> getCartWithProducts(int userId);

    @Query("SELECT p.*, c.quantity AS cartQuantity " +
            "FROM products p " +
            "INNER JOIN carts c ON p.id = c.product_id " +
            "WHERE c.user_id = :userId AND c.deleted_at IS NULL")
    List<Product> getCartWithProductsRaw(int userId);


    // === UPDATE ===
    @Query("UPDATE carts SET quantity = :quantity, updated_at = :updatedAt " +
            "WHERE user_id = :userId AND product_id = :productId")
    void updateQuantity(int userId, int productId, int quantity, Date updatedAt);

    @Query("UPDATE carts SET quantity = quantity + 1 " +
            "WHERE user_id = :userId AND product_id = :productId")
    void increaseQuantity(int userId, int productId);

    @Query("UPDATE carts SET quantity = quantity - 1 " +
            "WHERE user_id = :userId AND product_id = :productId AND quantity > 1")
    void decreaseQuantity(int userId, int productId);


    // === DELETE ===
    @Query("DELETE FROM carts WHERE user_id = :userId AND product_id = :productId")
    void deleteCartItem(int userId, int productId);

    @Query("DELETE FROM carts WHERE user_id = :userId")
    void clearCartByUserId(int userId);

    @Query("DELETE FROM carts WHERE id = :id")
    void deleteById(int id);

    // === COUNT ===
    @Query("SELECT SUM(quantity) FROM carts WHERE user_id = :userId")
    Integer totalItemsInCart(int userId);

    @Query("SELECT COUNT(*) FROM carts")
    Integer countCarts();
}


