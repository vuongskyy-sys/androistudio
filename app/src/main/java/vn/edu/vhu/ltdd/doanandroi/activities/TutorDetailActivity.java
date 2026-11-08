package vn.edu.vhu.ltdd.doanandroi.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.ActivityTutorDetailBinding;
import vn.edu.vhu.ltdd.doanandroi.databinding.DialogBookingBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Booking;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.FormatUtils;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class TutorDetailActivity extends AppCompatActivity {

    private ActivityTutorDetailBinding binding;
    private Tutor tutor;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTutorDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fix Status bar overlay
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setAppearanceLightStatusBars(true);

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        // Retrieve tutor from Intent
        tutor = (Tutor) getIntent().getSerializableExtra("tutor_data");
        if (tutor == null) {
            tutor = dbHelper.getTutorById("tut-01");
        }

        bindTutorData();
        setupActions();

        // Xử lý nút quay lại phần cứng / cử chỉ hệ thống
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void bindTutorData() {
        if (tutor == null) return;

        binding.tvDetailName.setText(tutor.getName());
        binding.tvDetailTitle.setText(tutor.getTitle());
        binding.tvDetailBadge.setText(tutor.getBadge());
        binding.tvDetailRating.setText("⭐ " + tutor.getRating() + " (" + tutor.getReviewsCount() + ")");
        binding.tvDetailDistrict.setText("📍 " + tutor.getDistrict().split(",")[0]);
        binding.tvDetailMode.setText("💻 " + tutor.getMode());
        binding.tvDetailSubjects.setText(tutor.getSubjects());
        binding.tvDetailBio.setText(tutor.getBio());
        binding.tvDetailAchievements.setText(tutor.getAchievements());
        binding.tvDetailHourlyRate.setText(FormatUtils.formatVND(tutor.getHourlyRate()) + "/h");

        Glide.with(this)
                .load(tutor.getAvatar())
                .placeholder(R.drawable.bg_avatar_placeholder)
                .error(R.drawable.bg_avatar_placeholder)
                .centerCrop()
                .into(binding.imgDetailAvatar);

        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        if (tutor == null) return;
        boolean isFav = dbHelper.isFavorite(tutor.getId());
        binding.btnDetailFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
    }

    private void setupActions() {
        // Nút quay lại trên Toolbar
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnDetailFavorite.setOnClickListener(v -> {
            if (tutor != null) {
                boolean newFav = dbHelper.toggleFavorite(tutor.getId());
                updateFavoriteIcon();
                String msg = newFav ? "Đã lưu gia sư vào mục yêu thích" : "Đã bỏ lưu gia sư";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnBookTrialNow.setOnClickListener(v -> showBookingDialog());
    }

    private void showBookingDialog() {
        if (tutor == null) return;

        DialogBookingBinding dialogBinding = DialogBookingBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .create();

        dialogBinding.tvDialogTutorName.setText("Gia sư: " + tutor.getName());

        String[] subs = tutor.getSubjects().split(",");
        dialogBinding.etSubject.setText(subs[0].trim());

        String[] timeSlots = new String[]{
                "Sáng (08:30 - 10:30)",
                "Chiều (14:30 - 16:30)",
                "Tối (19:00 - 21:00)"
        };
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        dialogBinding.spinnerTimeSlot.setAdapter(timeAdapter);

        String[] modes = new String[]{
                "Học Trực tiếp tại nhà",
                "Học Online qua Google Meet"
        };
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modes);
        dialogBinding.spinnerMode.setAdapter(modeAdapter);

        dialogBinding.etBookingDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(this, (view1, y, m, d) -> {
                String selectedDate = y + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d);
                dialogBinding.etBookingDate.setText(selectedDate);
            }, year, month, day);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();
        });

        User user = SharedPrefManager.getInstance(this).getUser();
        String parentName = (user != null && user.getName() != null) ? user.getName() : "Chị Mai Hương";
        String phone = (user != null && user.getPhone() != null) ? user.getPhone() : "0908 123 456";

        dialogBinding.btnCancelDialog.setOnClickListener(v -> dialog.dismiss());

        dialogBinding.btnConfirmBooking.setOnClickListener(v -> {
            String studentName = dialogBinding.etStudentName.getText().toString().trim();
            String studentGrade = dialogBinding.etStudentGrade.getText().toString().trim();
            String subject = dialogBinding.etSubject.getText().toString().trim();
            String date = dialogBinding.etBookingDate.getText().toString().trim();
            String timeSlot = dialogBinding.spinnerTimeSlot.getSelectedItem().toString();
            String mode = dialogBinding.spinnerMode.getSelectedItem().toString();
            String note = dialogBinding.etBookingNote.getText().toString().trim();

            if (studentName.isEmpty() || studentGrade.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên học sinh và khối lớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            String bookingId = "BK-2026-" + (int)(1000 + Math.random() * 9000);
            Booking booking = new Booking(
                    bookingId,
                    tutor.getId(),
                    tutor.getName(),
                    tutor.getAvatar(),
                    parentName,
                    phone,
                    studentName,
                    studentGrade,
                    subject,
                    mode,
                    date,
                    timeSlot,
                    note,
                    "Confirmed"
            );

            boolean success = dbHelper.insertBooking(booking);
            if (success) {
                Toast.makeText(this, "Đặt lịch học thử thành công! Mã: " + bookingId, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Không thể đặt lịch, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
