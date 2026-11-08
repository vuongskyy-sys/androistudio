package vn.edu.vhu.ltdd.doanandroi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import vn.edu.vhu.ltdd.doanandroi.adapters.BookingAdapter;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.FragmentScheduleBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Booking;

public class ScheduleFragment extends Fragment implements BookingAdapter.OnBookingCancelListener {

    private FragmentScheduleBinding binding;
    private DatabaseHelper dbHelper;
    private BookingAdapter bookingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());

        setupRecyclerView();
        loadBookings();
    }

    private void setupRecyclerView() {
        bookingAdapter = new BookingAdapter(requireContext(), this);
        binding.rvBookings.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBookings.setAdapter(bookingAdapter);
    }

    private void loadBookings() {
        if (dbHelper == null) return;
        List<Booking> list = dbHelper.getAllBookings();
        bookingAdapter.setBookings(list);

        int count = list.size();
        binding.tvStatBookings.setText(count + " Buổi");
        binding.tvStatHours.setText((count * 2.0) + " Giờ");

        if (list.isEmpty()) {
            binding.layoutEmptySchedule.setVisibility(View.VISIBLE);
            binding.rvBookings.setVisibility(View.GONE);
        } else {
            binding.layoutEmptySchedule.setVisibility(View.GONE);
            binding.rvBookings.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookings();
    }

    @Override
    public void onCancel(Booking booking) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Xác nhận hủy lịch học")
                .setMessage("Bạn có chắc chắn muốn hủy lịch học với gia sư " + booking.getTutorName() + " môn " + booking.getSubject() + " không?")
                .setPositiveButton("Hủy lịch", (dialog, which) -> {
                    boolean success = dbHelper.cancelBooking(booking.getId());
                    if (success) {
                        Toast.makeText(requireContext(), "Đã hủy lịch học thành công!", Toast.LENGTH_SHORT).show();
                        loadBookings();
                    } else {
                        Toast.makeText(requireContext(), "Không thể hủy, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Giữ lại", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
