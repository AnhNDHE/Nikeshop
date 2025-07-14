
package com.example.nikeshop.data.repositories;

import android.content.Context;

import com.example.nikeshop.data.local.AppDatabase;
import com.example.nikeshop.data.local.dao.ProductDao;
import com.example.nikeshop.data.local.entity.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private final ProductDao productDao;
    private final ExecutorService executorService;

    public ProductRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        productDao = db.productDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertProduct(Product product) {
        executorService.execute(() -> productDao.insertProduct(product));
    }

    public void updateProduct(Product product) {
        executorService.execute(() -> productDao.updateProduct(product));
    }

    public void deleteProductById(int id) {
        executorService.execute(() -> productDao.deleteProductById(id));
    }

    public void getAllProducts(Callback<List<Product>> callback) {
        executorService.execute(() -> {
            List<Product> products = productDao.getAllProducts();
            if (callback != null) {
                callback.onResult(products);
            }
        });
    }

    public void getProductById(int id, Callback<Product> callback) {
        executorService.execute(() -> {
            Product product = productDao.getProductById(id);
            if (callback != null) {
                callback.onResult(product);
            }
        });
    }
   

    public interface Callback<T> {
        void onResult(T result);
    }
}
