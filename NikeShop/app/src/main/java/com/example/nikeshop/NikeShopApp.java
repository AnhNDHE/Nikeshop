package com.example.nikeshop;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.nikeshop.data.local.AppDatabase;
import com.example.nikeshop.data.local.seed.Seeder;

public class NikeShopApp extends Application {

    private static NikeShopApp instance;
    private static AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Init databse
        database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "nike_shop_db"
        ).fallbackToDestructiveMigration().build();
        Log.d("DEBUG_DB", getDatabasePath("nike_shop_db.db").getAbsolutePath());
        // Init seeder gen data
        Seeder.seed(this);
    }

    public static NikeShopApp getInstance() {
        return instance;
    }
    public static AppDatabase getDatabase() {
        return database;
    }
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }
}