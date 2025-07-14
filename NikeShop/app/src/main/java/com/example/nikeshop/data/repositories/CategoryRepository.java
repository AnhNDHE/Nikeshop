package com.example.nikeshop.data.repositories;

import android.content.Context;
import com.example.nikeshop.data.local.AppDatabase;
import com.example.nikeshop.data.local.dao.CategoryDao;
import com.example.nikeshop.data.local.entity.Category;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final ExecutorService executorService;

    public CategoryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        categoryDao = db.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void getAllCategories(Callback<List<Category>> callback) {
        executorService.execute(() -> {
            List<Category> categories = categoryDao.getAllCategories();
            if (callback != null) {
                callback.onResult(categories);
            }
        });
    }

    public interface Callback<T> {
        void onResult(T result);
    }
}
