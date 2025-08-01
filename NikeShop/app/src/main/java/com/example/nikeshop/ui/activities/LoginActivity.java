package com.example.nikeshop.ui.activities;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
// Thêm vào đầu file
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikeshop.data.local.AppDatabase;
import com.example.nikeshop.data.local.dao.UserDao;
import com.example.nikeshop.data.local.entity.User;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import android.app.NotificationManager;

import com.example.nikeshop.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE); // ✅ chỉ gọi 1 lần ở đây

        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
        if (isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView signUpLink = findViewById(R.id.signUpLink);
        signUpLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUp.class)));

        TextView forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        forgotPasswordLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, AccountRecovery.class)));

        TextView skipNow = findViewById(R.id.skipNow);
        skipNow.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        });

        EditText emailInput = findViewById(R.id.emailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "nike_shop_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            String hashedInput = hashPassword(password);
            User user = userDao.loginUser(email, hashedInput);
            Log.d("LoginDebug", "Hashed input = " + hashedInput);

            if (user != null) {
                prefs.edit()
                        .putBoolean("is_logged_in", true)
                        .putInt("user_id", user.getId())
                        .putString("user_email", user.getEmail())
                        .putString("user_name", user.getUsername())
                        .putBoolean("is_admin", user.isAdmin())
                        .apply();

                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent;
                if (user.isAdmin()) {
                    intent = new Intent(LoginActivity.this, DashboardActivity.class); // activity cho admin
                } else {
                    intent = new Intent(LoginActivity.this, HomeActivity.class); // activity cho người dùng thường
                }

                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        });

        sendTestNotification(); // có thể giữ nguyên hoặc bỏ nếu không cần

    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendTestNotification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        Notification notification = new NotificationCompat.Builder(this, "login_channel_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // dùng icon mặc định hoặc thay icon app bạn
                .setContentTitle("Welcome to NikeShop")
                .setContentText("This is a test notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }

    }

}