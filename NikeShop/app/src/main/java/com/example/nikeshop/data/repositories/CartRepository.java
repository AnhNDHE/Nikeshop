package com.example.nikeshop.data.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nikeshop.NikeShopApp;
import com.example.nikeshop.data.local.AppDatabase;
import com.example.nikeshop.data.local.dao.CartDao;
import com.example.nikeshop.data.local.entity.Cart;
import com.example.nikeshop.data.local.entity.Product;
import com.example.nikeshop.util.AppExecutors;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepository {

    private final CartDao cartDao;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MediatorLiveData<List<Product>> cartProductsLiveData = new MediatorLiveData<>();
    public CartRepository(Context context) {
        AppDatabase db = NikeShopApp.getDatabase();
        this.cartDao = db.cartDao();
    }

    public void insertAll(List<Cart> carts) {
        executor.execute(() -> cartDao.insertAll(carts));
    }

    public void deleteById(int cartId) {
        executor.execute(() -> cartDao.deleteById(cartId));
    }


    // Lấy danh sách cart theo userId (LiveData để quan sát thay đổi)
    public LiveData<List<Product>> getCartByUser(int userId) {
        return cartDao.getCartByUser(userId);
    }

    // Thêm 1 sản phẩm vào cart
    public void addToCart(int userId, int productId, int quantity) {
        executor.execute(() -> {
            Cart existing = cartDao.getCartItem(userId, productId);
            Date now = new Date();
            if (existing != null) {
                int newQty = existing.getQuantity() + quantity;
                cartDao.updateQuantity(userId, productId, newQty, now);
            } else {
                Cart cart = new Cart(userId, productId, quantity, now, now, null);
                cartDao.insert(cart);
            }
        });
    }

    // Cập nhật số lượng
    public void updateQuantity(int userId, int productId, int quantity) {
        executor.execute(() -> {
            Date now = new Date();
            cartDao.updateQuantity(userId, productId, quantity, now);
        });
    }

    // Xoá sản phẩm khỏi cart
    public void deleteItem(int userId, int productId) {
        executor.execute(() -> {
            cartDao.deleteCartItem(userId, productId);
        });
    }

    // Xoá toàn bộ cart của user
    public void clearCart(int userId) {
        executor.execute(() -> {
            cartDao.clearCartByUserId(userId);
        });
    }

    // Lấy tổng số sản phẩm trong giỏ hàng (dạng LiveData)
    public LiveData<Integer> getTotalItemsInCart(int userId) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        executor.execute(() -> {
            Integer total = cartDao.totalItemsInCart(userId);
            liveData.postValue(total != null ? total : 0);
        });
        return liveData;
    }
    public LiveData<List<Product>> getCartProductsLive(int userId) {
        return cartDao.getCartWithProducts(userId);
    }


    public void refreshCartProducts(int userId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Product> updated = cartDao.getCartWithProductsRaw(userId);
            cartProductsLiveData.postValue(updated);
        });
    }
    public void increaseQuantity(int userId, int productId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            cartDao.increaseQuantity(userId, productId);
            refreshCartProducts(userId);  // <- cập nhật LiveData sau khi tăng
            Log.d("Repo", "Increased qty for product " + productId);
        });
    }

    public void decreaseQuantity(int userId, int productId) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            cartDao.decreaseQuantity(userId, productId);
            refreshCartProducts(userId);  // <- cập nhật LiveData sau khi giảm
            Log.d("Repo", "Decreased qty for product " + productId);
        });
    }
}
