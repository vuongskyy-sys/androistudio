package vn.edu.vhu.ltdd.doanandroi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.databinding.ItemBookingCardBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Booking;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnBookingCancelListener {
        void onCancel(Booking booking);
    }

    private final Context context;
    private List<Booking> bookingList = new ArrayList<>();
    private final OnBookingCancelListener cancelListener;

    public BookingAdapter(Context context, OnBookingCancelListener cancelListener) {
        this.context = context;
        this.cancelListener = cancelListener;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookingList = bookings != null ? bookings : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingCardBinding binding = ItemBookingCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookingCardBinding binding;

        public BookingViewHolder(@NonNull ItemBookingCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Booking booking) {
            binding.tvBookingSubject.setText(booking.getSubject());
            binding.tvBookingTutorName.setText("Gia sư: " + booking.getTutorName());
            binding.tvBookingStudent.setText("Học sinh: " + booking.getStudentName() + " (" + booking.getStudentGrade() + ")");
            binding.tvBookingDateTime.setText("📅 " + booking.getDate() + " • ⏰ " + booking.getTimeSlot());
            binding.tvBookingMode.setText("📍 " + booking.getMode());

            if ("Confirmed".equalsIgnoreCase(booking.getStatus())) {
                binding.tvBookingStatus.setText("Đã xác nhận");
                binding.tvBookingStatus.setTextColor(context.getColor(R.color.mint_600));
                binding.tvBookingStatus.setBackgroundResource(R.drawable.bg_badge_mint);
            } else {
                binding.tvBookingStatus.setText("Đang chờ duyệt");
                binding.tvBookingStatus.setTextColor(context.getColor(R.color.academic_lang));
                binding.tvBookingStatus.setBackgroundResource(R.drawable.bg_badge_academic);
            }

            Glide.with(context)
                    .load(booking.getTutorAvatar())
                    .placeholder(R.drawable.bg_avatar_placeholder)
                    .error(R.drawable.bg_avatar_placeholder)
                    .centerCrop()
                    .into(binding.imgBookingAvatar);

            binding.btnCancelBooking.setOnClickListener(v -> {
                if (cancelListener != null) {
                    cancelListener.onCancel(booking);
                }
            });
        }
    }
}
