package vn.edu.vhu.ltdd.doanandroi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import vn.edu.vhu.ltdd.doanandroi.MainActivity;
import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.ActivityRegisterBinding;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private DatabaseHelper dbHelper;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
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

        binding.btnBackToLogin.setOnClickListener(v -> finish());
        binding.tvBackToLoginLink.setOnClickListener(v -> finish());
        binding.btnRegisterSubmit.setOnClickListener(v -> performRegister());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void performRegister() {
        String name = binding.etRegName.getText().toString().trim();
        String email = binding.etRegEmail.getText().toString().trim();
        String phone = binding.etRegPhone.getText().toString().trim();
        String password = binding.etRegPassword.getText().toString().trim();
        String confirmPassword = binding.etRegConfirmPassword.getText().toString().trim();
        String role = binding.rbParent.isChecked() ? "parent" : "tutor";

        // Form Validation
        if (name.isEmpty()) {
            binding.tilRegName.setError(getString(R.string.err_empty_name));
            binding.etRegName.requestFocus();
            return;
        } else {
            binding.tilRegName.setError(null);
        }

        if (email.isEmpty()) {
            binding.tilRegEmail.setError(getString(R.string.err_empty_email));
            binding.etRegEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilRegEmail.setError(getString(R.string.err_invalid_email));
            binding.etRegEmail.requestFocus();
            return;
        } else {
            binding.tilRegEmail.setError(null);
        }

        if (phone.isEmpty()) {
            binding.tilRegPhone.setError(getString(R.string.err_empty_phone));
            binding.etRegPhone.requestFocus();
            return;
        } else {
            binding.tilRegPhone.setError(null);
        }

        if (password.isEmpty()) {
            binding.tilRegPassword.setError(getString(R.string.err_empty_password));
            binding.etRegPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            binding.tilRegPassword.setError(getString(R.string.err_short_password));
            binding.etRegPassword.requestFocus();
            return;
        } else {
            binding.tilRegPassword.setError(null);
        }

        if (!password.equals(confirmPassword)) {
            binding.tilRegConfirmPassword.setError(getString(R.string.err_password_not_match));
            binding.etRegConfirmPassword.requestFocus();
            return;
        } else {
            binding.tilRegConfirmPassword.setError(null);
        }

        // Check if email already registered
        if (dbHelper.checkEmailExists(email)) {
            binding.tilRegEmail.setError(getString(R.string.msg_email_exists));
            binding.etRegEmail.requestFocus();
            return;
        }

        User newUser = new User(
                "usr-" + System.currentTimeMillis(),
                name,
                email,
                phone,
                password,
                role,
                "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&q=80&w=200"
        );

        boolean success = dbHelper.registerUser(newUser);
        if (success) {
            prefManager.saveUser(newUser);
            Toast.makeText(this, getString(R.string.msg_register_success), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đăng ký không thành công, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }
}
