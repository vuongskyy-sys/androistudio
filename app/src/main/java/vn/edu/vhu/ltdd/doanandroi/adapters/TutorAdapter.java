package vn.edu.vhu.ltdd.doanandroi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.ItemTutorCardBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;
import vn.edu.vhu.ltdd.doanandroi.utils.FormatUtils;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    public interface OnTutorClickListener {
        void onDetailClick(Tutor tutor);
        void onBookClick(Tutor tutor);
        void onFavoriteClick(Tutor tutor, boolean isFav);
    }

    private final Context context;
    private List<Tutor> tutorList = new ArrayList<>();
    private final OnTutorClickListener listener;
    private final DatabaseHelper dbHelper;

    public TutorAdapter(Context context, OnTutorClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.dbHelper = new DatabaseHelper(context);
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutorList = tutors != null ? tutors : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTutorCardBinding binding = ItemTutorCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TutorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        Tutor tutor = tutorList.get(position);
        holder.bind(tutor);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    class TutorViewHolder extends RecyclerView.ViewHolder {
        private final ItemTutorCardBinding binding;

        public TutorViewHolder(@NonNull ItemTutorCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Tutor tutor) {
            binding.tvTutorName.setText(tutor.getName());
            binding.tvTutorTitle.setText(tutor.getTitle());
            binding.tvRating.setText(String.valueOf(tutor.getRating()));
            binding.tvReviewsCount.setText("(" + tutor.getReviewsCount() + ")");
            binding.tvDistrict.setText(tutor.getDistrict().split(",")[0]);
            binding.tvSubjects.setText(tutor.getSubjects());
            binding.tvHourlyRate.setText(FormatUtils.formatVND(tutor.getHourlyRate()) + "/h");

            binding.imgVerifiedBadge.setVisibility(tutor.isVerified() ? View.VISIBLE : View.GONE);

            // Load Avatar with Glide
            Glide.with(context)
                    .load(tutor.getAvatar())
                    .placeholder(R.drawable.bg_avatar_placeholder)
                    .error(R.drawable.bg_avatar_placeholder)
                    .centerCrop()
                    .into(binding.imgTutorAvatar);

            // Check favorite state
            boolean isFav = dbHelper.isFavorite(tutor.getId());
            binding.btnFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart);

            binding.btnFavorite.setOnClickListener(v -> {
                boolean newFavState = dbHelper.toggleFavorite(tutor.getId());
                binding.btnFavorite.setImageResource(newFavState ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
                if (listener != null) {
                    listener.onFavoriteClick(tutor, newFavState);
                }
            });

            binding.btnViewProfile.setOnClickListener(v -> {
                if (listener != null) listener.onDetailClick(tutor);
            });

            binding.btnBookTrial.setOnClickListener(v -> {
                if (listener != null) listener.onBookClick(tutor);
            });

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onDetailClick(tutor);
            });
        }
    }
}
