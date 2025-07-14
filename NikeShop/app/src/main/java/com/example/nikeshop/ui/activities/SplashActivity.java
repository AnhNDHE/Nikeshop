package com.example.nikeshop.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nikeshop.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2000; // thời gian chờ splash (ms)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kiểm tra xem đây có phải là lần đầu mở app không
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            // Hiển thị màn hình splash lần đầu
            setContentView(R.layout.activity_splash);

            // Delay rồi chuyển sang LoginActivity
            new Handler().postDelayed(() -> {
                // Đánh dấu là đã chạy lần đầu
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isFirstRun", false);
                editor.apply();

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_TIME);
        } else {
            // Bỏ qua splash, chuyển thẳng sang LoginActivity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
