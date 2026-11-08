package vn.edu.vhu.ltdd.doanandroi.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.List;

import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.activities.TutorDetailActivity;
import vn.edu.vhu.ltdd.doanandroi.adapters.TutorAdapter;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.DialogBookingBinding;
import vn.edu.vhu.ltdd.doanandroi.databinding.FragmentTutorsBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Booking;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class TutorsFragment extends Fragment implements TutorAdapter.OnTutorClickListener {

    private FragmentTutorsBinding binding;
    private DatabaseHelper dbHelper;
    private TutorAdapter tutorAdapter;
    private String currentCategory = "all";
    private String currentSearchQuery = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());

        setupRecyclerView();
        setupSearchView();
        setupChipGroup();

        loadTutors();
    }

    private void setupRecyclerView() {
        tutorAdapter = new TutorAdapter(requireContext(), this);
        binding.rvTutors.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTutors.setAdapter(tutorAdapter);
    }

    private void setupSearchView() {
        binding.searchViewTutors.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearchQuery = query;
                loadTutors();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                loadTutors();
                return true;
            }
        });
    }

    private void setupChipGroup() {
        binding.chipGroupSubjects.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;
            int id = checkedIds.get(0);
            if (id == R.id.chipAll) {
                currentCategory = "all";
            } else if (id == R.id.chipMath) {
                currentCategory = "math";
            } else if (id == R.id.chipLang) {
                currentCategory = "languages";
            } else if (id == R.id.chipScience) {
                currentCategory = "sciences";
            } else if (id == R.id.chipTech) {
                currentCategory = "tech";
            }
            loadTutors();
        });
    }

    public void setCategoryFilter(String category) {
        this.currentCategory = category != null ? category : "all";
        if (binding != null) {
            if ("math".equalsIgnoreCase(category)) {
                binding.chipMath.setChecked(true);
            } else if ("languages".equalsIgnoreCase(category)) {
                binding.chipLang.setChecked(true);
            } else if ("sciences".equalsIgnoreCase(category)) {
                binding.chipScience.setChecked(true);
            } else if ("tech".equalsIgnoreCase(category)) {
                binding.chipTech.setChecked(true);
            } else {
                binding.chipAll.setChecked(true);
            }
        }
        loadTutors();
    }

    private void loadTutors() {
        if (dbHelper == null) return;
        List<Tutor> list = dbHelper.searchTutors(currentSearchQuery, currentCategory);
        tutorAdapter.setTutors(list);

        if (list.isEmpty()) {
            binding.layoutEmptyTutors.setVisibility(View.VISIBLE);
            binding.rvTutors.setVisibility(View.GONE);
        } else {
            binding.layoutEmptyTutors.setVisibility(View.GONE);
            binding.rvTutors.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTutors();
    }

    @Override
    public void onDetailClick(Tutor tutor) {
        Intent intent = new Intent(requireContext(), TutorDetailActivity.class);
        intent.putExtra("tutor_data", tutor);
        startActivity(intent);
    }

    @Override
    public void onBookClick(Tutor tutor) {
        showBookingDialog(tutor);
    }

    @Override
    public void onFavoriteClick(Tutor tutor, boolean isFav) {
        String msg = isFav ? "Đã lưu " + tutor.getName() + " vào danh sách yêu thích" : "Đã bỏ lưu gia sư";
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showBookingDialog(Tutor tutor) {
        DialogBookingBinding dialogBinding = DialogBookingBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.getRoot())
                .create();

        dialogBinding.tvDialogTutorName.setText("Gia sư: " + tutor.getName() + " • " + tutor.getTitle());

        String[] firstSubject = tutor.getSubjects().split(",");
        dialogBinding.etSubject.setText(firstSubject[0].trim());

        // Setup Spinners
        String[] timeSlots = new String[]{
                "Sáng (08:30 - 10:30)",
                "Chiều (14:30 - 16:30)",
                "Tối (19:00 - 21:00)"
        };
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeSlots);
        dialogBinding.spinnerTimeSlot.setAdapter(timeAdapter);

        String[] modes = new String[]{
                "Học Trực tiếp tại nhà",
                "Học Online qua Google Meet"
        };
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, modes);
        dialogBinding.spinnerMode.setAdapter(modeAdapter);

        // Date Picker for etBookingDate
        dialogBinding.etBookingDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(requireContext(), (view1, y, m, d) -> {
                String selectedDate = y + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d);
                dialogBinding.etBookingDate.setText(selectedDate);
            }, year, month, day);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();
        });

        // Current User Info
        User user = SharedPrefManager.getInstance(requireContext()).getUser();
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
                Toast.makeText(requireContext(), "Vui lòng nhập tên học sinh và khối lớp!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(requireContext(), "Đặt lịch học thử thành công! Mã: " + bookingId, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Không thể đặt lịch, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
