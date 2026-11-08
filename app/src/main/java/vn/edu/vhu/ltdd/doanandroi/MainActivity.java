package vn.edu.vhu.ltdd.doanandroi;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.edu.vhu.ltdd.doanandroi.databinding.ActivityMainBinding;
import vn.edu.vhu.ltdd.doanandroi.fragments.HomeFragment;
import vn.edu.vhu.ltdd.doanandroi.fragments.ProfileFragment;
import vn.edu.vhu.ltdd.doanandroi.fragments.ScheduleFragment;
import vn.edu.vhu.ltdd.doanandroi.fragments.TutorsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final HomeFragment homeFragment = new HomeFragment();
    private final TutorsFragment tutorsFragment = new TutorsFragment();
    private final ScheduleFragment scheduleFragment = new ScheduleFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();

    private long backPressedTime = 0;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Fix Status Bar / Tai thỏ / Camera nốt ruồi đè chữ
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightStatusBars(true);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // 2. Load HomeFragment as default tab
        loadFragment(homeFragment);

        // 3. Setup Bottom Navigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                loadFragment(homeFragment);
                return true;
            } else if (itemId == R.id.navigation_tutors) {
                loadFragment(tutorsFragment);
                return true;
            } else if (itemId == R.id.navigation_schedule) {
                loadFragment(scheduleFragment);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                loadFragment(profileFragment);
                return true;
            }
            return false;
        });

        // 4. Xử lý nút quay lại (Back navigation chuẩn UX)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Nếu đang ở các tab khác (Gia sư, Lịch học, Cá nhân) -> Bấm quay lại sẽ về Trang chủ
                if (binding.bottomNavigation.getSelectedItemId() != R.id.navigation_home) {
                    binding.bottomNavigation.setSelectedItemId(R.id.navigation_home);
                } else {
                    // Nếu đang ở Trang chủ -> Nhấn 2 lần trong 2 giây để thoát
                    if (backPressedTime + 2000 > System.currentTimeMillis()) {
                        if (backToast != null) backToast.cancel();
                        finish();
                    } else {
                        backToast = Toast.makeText(MainActivity.this, "Nhấn quay lại lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT);
                        backToast.show();
                        backPressedTime = System.currentTimeMillis();
                    }
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public void navigateToTutorsTab(String category) {
        binding.bottomNavigation.setSelectedItemId(R.id.navigation_tutors);
        tutorsFragment.setCategoryFilter(category);
    }
}
