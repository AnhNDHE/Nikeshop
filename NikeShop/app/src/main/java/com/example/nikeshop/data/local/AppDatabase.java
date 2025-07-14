package com.example.nikeshop.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.nikeshop.data.DateConverter;
import com.example.nikeshop.data.SizeListConverter;
import com.example.nikeshop.data.local.dao.CartDao;
import com.example.nikeshop.data.local.dao.CategoryDao;
import com.example.nikeshop.data.local.dao.CouponDao;
import com.example.nikeshop.data.local.dao.OrderDao;
import com.example.nikeshop.data.local.dao.OrderDetailDao;
import com.example.nikeshop.data.local.dao.ProductDao;
import com.example.nikeshop.data.local.dao.ReviewDao;
import com.example.nikeshop.data.local.dao.UserDao;
import com.example.nikeshop.data.local.dao.WishlistDao;
import com.example.nikeshop.data.local.entity.Cart;
import com.example.nikeshop.data.local.entity.Category;
import com.example.nikeshop.data.local.entity.Product;
import com.example.nikeshop.data.local.entity.User;
import com.example.nikeshop.data.local.entity.Coupon;
import com.example.nikeshop.data.local.entity.Review;
import com.example.nikeshop.data.local.entity.Wishlist;
import com.example.nikeshop.data.local.entity.Order;
import com.example.nikeshop.data.local.entity.OrderDetail;
@Database(
        entities = {
                User.class,
                Product.class,
                Category.class,
                Cart.class,
                Coupon.class,
                Review.class,
                Wishlist.class,
                Order.class,
                OrderDetail.class,
        }, version = 2)

@TypeConverters({DateConverter.class, SizeListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract CartDao cartDao();
    public abstract CouponDao couponDao();
    public abstract ReviewDao reviewDao();
    public abstract WishlistDao wishlistDao();
    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "nike_shop_db")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    new Thread(() -> {
                                        AppDatabase database = getInstance(context);
                                        CategoryDao categoryDao = database.categoryDao();
                                        ProductDao productDao = database.productDao();
                                        // Thêm 4 category
                                        long sneakersId = categoryDao.insertCategory(new Category("Sneakers", null, null, null));
                                        long runnersId = categoryDao.insertCategory(new Category("Runners", null, null, null));
                                        long casualsId = categoryDao.insertCategory(new Category("Casuals", null, null, null));
                                        long jordansId = categoryDao.insertCategory(new Category("Jordans", null, null, null));

                                        // Thêm 4 sản phẩm cho mỗi category với nhiều size
                                        // Sneakers
                                        productDao.insertProduct(new Product("Nike Air 1", "Basketball shoes", 244.99, java.util.Arrays.asList("40", "41", "42", "43"), 10, "url1", (int)sneakersId, "Nike", "Red", "Leather", "SKU001", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air 2", "Basketball shoes", 199.99, java.util.Arrays.asList("40", "41", "42"), 8, "url2", (int)sneakersId, "Nike", "Blue", "Leather", "SKU002", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air 3", "Basketball shoes", 179.99, java.util.Arrays.asList("41", "42", "43"), 12, "url3", (int)sneakersId, "Nike", "Black", "Leather", "SKU003", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air 4", "Basketball shoes", 159.99, java.util.Arrays.asList("40", "42"), 7, "url4", (int)sneakersId, "Nike", "White", "Leather", "SKU004", null, null, null));

                                        // Runners
                                        productDao.insertProduct(new Product("Nike Run 1", "Jogging shoes", 44.99, java.util.Arrays.asList("40", "41", "42", "43"), 15, "url5", (int)runnersId, "Nike", "Black", "Mesh", "SKU005", null, null, null));
                                        productDao.insertProduct(new Product("Nike Run 2", "Jogging shoes", 54.99, java.util.Arrays.asList("41", "42", "43"), 10, "url6", (int)runnersId, "Nike", "Blue", "Mesh", "SKU006", null, null, null));
                                        productDao.insertProduct(new Product("Nike Run 3", "Jogging shoes", 64.99, java.util.Arrays.asList("40", "42", "43"), 9, "url7", (int)runnersId, "Nike", "Grey", "Mesh", "SKU007", null, null, null));
                                        productDao.insertProduct(new Product("Nike Run 4", "Jogging shoes", 74.99, java.util.Arrays.asList("40", "41"), 11, "url8", (int)runnersId, "Nike", "White", "Mesh", "SKU008", null, null, null));

                                        // Casuals
                                        productDao.insertProduct(new Product("Nike Casual 1", "Casual shoes", 124.99, java.util.Arrays.asList("40", "41", "42", "43"), 13, "url9", (int)casualsId, "Nike", "Black", "Canvas", "SKU009", null, null, null));
                                        productDao.insertProduct(new Product("Nike Casual 2", "Casual shoes", 134.99, java.util.Arrays.asList("41", "42", "43"), 9, "url10", (int)casualsId, "Nike", "Blue", "Canvas", "SKU010", null, null, null));
                                        productDao.insertProduct(new Product("Nike Casual 3", "Casual shoes", 144.99, java.util.Arrays.asList("40", "42", "43"), 8, "url11", (int)casualsId, "Nike", "Grey", "Canvas", "SKU011", null, null, null));
                                        productDao.insertProduct(new Product("Nike Casual 4", "Casual shoes", 154.99, java.util.Arrays.asList("40", "41"), 10, "url12", (int)casualsId, "Nike", "White", "Canvas", "SKU012", null, null, null));

                                        // Jordans
                                        productDao.insertProduct(new Product("Nike Air Jordans 1", "Jordans", 244.99, java.util.Arrays.asList("40", "41", "42", "43"), 6, "url13", (int)jordansId, "Nike", "Black", "Leather", "SKU013", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air Jordans 2", "Jordans", 254.99, java.util.Arrays.asList("41", "42", "43"), 5, "url14", (int)jordansId, "Nike", "Red", "Leather", "SKU014", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air Jordans 3", "Jordans", 264.99, java.util.Arrays.asList("40", "42", "43"), 7, "url15", (int)jordansId, "Nike", "Blue", "Leather", "SKU015", null, null, null));
                                        productDao.insertProduct(new Product("Nike Air Jordans 4", "Jordans", 274.99, java.util.Arrays.asList("40", "41"), 4, "url16", (int)jordansId, "Nike", "White", "Leather", "SKU016", null, null, null));
                                    }).start();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
