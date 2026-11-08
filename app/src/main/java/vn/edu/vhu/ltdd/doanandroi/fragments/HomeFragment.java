package vn.edu.vhu.ltdd.doanandroi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import vn.edu.vhu.ltdd.doanandroi.MainActivity;
import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.activities.TutorDetailActivity;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.FragmentHomeBinding;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseHelper dbHelper;
    private Tutor featuredTutor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());

        // Display user greeting
        User currentUser = SharedPrefManager.getInstance(requireContext()).getUser();
        if (currentUser != null && currentUser.getName() != null) {
            binding.tvGreeting.setText("Xin chào, " + currentUser.getName() + "!");
            String roleText = "parent".equalsIgnoreCase(currentUser.getRole()) ? "TÀI KHOẢN PHỤ HUYNH" :
                    "tutor".equalsIgnoreCase(currentUser.getRole()) ? "TÀI KHOẢN GIA SƯ" : "QUẢN TRỊ VIÊN";
            binding.tvUserRoleBadge.setText(roleText);
        }

        // Hero button -> Switch to Tutors Tab
        binding.btnHeroExplore.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTutorsTab("all");
            }
        });

        // Category Buttons -> Switch to Tutors Tab with Filter
        binding.btnCatMath.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTutorsTab("math");
            }
        });

        binding.btnCatLang.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTutorsTab("languages");
            }
        });

        binding.btnCatScience.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTutorsTab("sciences");
            }
        });

        binding.btnCatTech.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToTutorsTab("tech");
            }
        });

        // Load Featured Tutor (tut-01)
        featuredTutor = dbHelper.getTutorById("tut-01");
        if (featuredTutor != null) {
            binding.tvFeaturedName.setText(featuredTutor.getName());
            binding.tvFeaturedTitle.setText(featuredTutor.getTitle());
            binding.tvFeaturedRating.setText("⭐ " + featuredTutor.getRating() + " (" + featuredTutor.getReviewsCount() + " đánh giá) • 250.000₫/h");

            Glide.with(this)
                    .load(featuredTutor.getAvatar())
                    .placeholder(R.drawable.bg_avatar_placeholder)
                    .error(R.drawable.bg_avatar_placeholder)
                    .centerCrop()
                    .into(binding.imgFeaturedAvatar);

            View.OnClickListener openFeaturedDetail = v -> {
                Intent intent = new Intent(requireContext(), TutorDetailActivity.class);
                intent.putExtra("tutor_data", featuredTutor);
                startActivity(intent);
            };

            binding.cardFeaturedTutor.setOnClickListener(openFeaturedDetail);
            binding.btnFeaturedBook.setOnClickListener(openFeaturedDetail);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
