package vn.edu.vhu.ltdd.doanandroi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.vhu.ltdd.doanandroi.MainActivity;
import vn.edu.vhu.ltdd.doanandroi.databinding.ActivitySplashBinding;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean isLoggedIn = SharedPrefManager.getInstance(this).isLoggedIn();
            Intent intent;
            if (isLoggedIn) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1800);
    }
}
