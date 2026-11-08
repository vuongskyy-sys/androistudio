package vn.edu.vhu.ltdd.doanandroi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import vn.edu.vhu.ltdd.doanandroi.MainActivity;
import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.ActivityLoginBinding;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private DatabaseHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fix status bar
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightStatusBars(true);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        prefManager = SharedPrefManager.getInstance(this);

        setupButtons();
    }

    private void setupButtons() {
        binding.btnLogin.setOnClickListener(v -> performLogin());

        binding.tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Fast 1-Click Demo Buttons for Testing
        binding.btnDemoParent.setOnClickListener(v -> {
            binding.etEmail.setText("parent@educonnect.vn");
            binding.etPassword.setText("123456");
            performLogin();
        });

        binding.btnDemoTutor.setOnClickListener(v -> {
            binding.etEmail.setText("tutor@educonnect.vn");
            binding.etPassword.setText("123456");
            performLogin();
        });
    }

    private void performLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // Form Validation
        if (email.isEmpty()) {
            binding.tilEmail.setError(getString(R.string.err_empty_email));
            binding.etEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError(getString(R.string.err_invalid_email));
            binding.etEmail.requestFocus();
            return;
        } else {
            binding.tilEmail.setError(null);
        }

        if (password.isEmpty()) {
            binding.tilPassword.setError(getString(R.string.err_empty_password));
            binding.etPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            binding.tilPassword.setError(getString(R.string.err_short_password));
            binding.etPassword.requestFocus();
            return;
        } else {
            binding.tilPassword.setError(null);
        }

        // Authenticate with SQLite Database
        User user = dbHelper.loginUser(email, password);
        if (user != null) {
            prefManager.saveUser(user);
            Toast.makeText(this, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.msg_login_failed), Toast.LENGTH_LONG).show();
        }
    }
}
