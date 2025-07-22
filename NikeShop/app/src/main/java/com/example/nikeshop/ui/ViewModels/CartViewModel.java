package com.example.nikeshop.ui.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nikeshop.data.local.entity.Cart;
import com.example.nikeshop.data.local.entity.Product;
import com.example.nikeshop.data.repositories.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final CartRepository repository;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
    }

    public LiveData<List<Product>> getCartByUser(int userId) {
        return repository.getCartByUser(userId);
    }

    public void addToCart(int userId, int productId, int quantity) {
        repository.addToCart(userId, productId, quantity);
    }

    public void updateQuantity(int userId, int productId, int quantity) {
        repository.updateQuantity(userId, productId, quantity);
    }

    public void removeFromCart(int userId, int productId) {
        repository.deleteItem(userId, productId);
    }

    public void clearCart(int userId) {
        repository.clearCart(userId);
    }

    public LiveData<Integer> getTotalItemCount(int userId) {
        return repository.getTotalItemsInCart(userId);
    }

    public void increaseQuantity(int userId, int productId) {
        repository.increaseQuantity(userId, productId);
    }

    public void decreaseQuantity(int userId, int productId) {
        repository.decreaseQuantity(userId, productId);
    }

    public void insertAll(List<Cart> carts) {
        repository.insertAll(carts);
    }

    public void deleteById(int cartId) {
        repository.deleteById(cartId);
    }

    public LiveData<List<Product>> getCartWithProducts(int userId) {
        return repository.getCartProductsLive(userId);
    }


}
