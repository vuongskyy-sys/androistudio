package vn.edu.vhu.ltdd.doanandroi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import vn.edu.vhu.ltdd.doanandroi.R;
import vn.edu.vhu.ltdd.doanandroi.activities.LoginActivity;
import vn.edu.vhu.ltdd.doanandroi.database.DatabaseHelper;
import vn.edu.vhu.ltdd.doanandroi.databinding.DialogEditProfileBinding;
import vn.edu.vhu.ltdd.doanandroi.databinding.FragmentProfileBinding;
import vn.edu.vhu.ltdd.doanandroi.models.User;
import vn.edu.vhu.ltdd.doanandroi.utils.SharedPrefManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private DatabaseHelper dbHelper;
    private SharedPrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(requireContext());
        prefManager = SharedPrefManager.getInstance(requireContext());

        loadUserProfile();
        setupActions();
    }

    private void loadUserProfile() {
        User user = prefManager.getUser();
        if (user != null) {
            binding.tvProfileName.setText(user.getName());
            binding.tvProfileEmail.setText(user.getEmail());
            binding.tvProfilePhone.setText(user.getPhone());

            String roleText = "parent".equalsIgnoreCase(user.getRole()) ? "Phụ huynh học viên" :
                    "tutor".equalsIgnoreCase(user.getRole()) ? "Gia sư / Giảng viên" : "Quản trị viên";
            binding.tvProfileRole.setText(roleText);

            Glide.with(this)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.bg_avatar_placeholder)
                    .error(R.drawable.bg_avatar_placeholder)
                    .centerCrop()
                    .into(binding.imgProfileAvatar);
        }

        // Load stats
        int favCount = dbHelper.getFavoriteCount();
        int bookCount = dbHelper.getBookingsCount();

        binding.tvStatFavoritesCount.setText(String.valueOf(favCount));
        binding.tvStatTotalBookingsCount.setText(String.valueOf(bookCount));
    }

    private void setupActions() {
        binding.btnEditProfile.setOnClickListener(v -> showEditProfileDialog());

        binding.btnLogout.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Đăng xuất")
                    .setMessage(R.string.confirm_logout)
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        prefManager.logout();
                        Toast.makeText(requireContext(), "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void showEditProfileDialog() {
        DialogEditProfileBinding editBinding = DialogEditProfileBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setView(editBinding.getRoot())
                .create();

        User current = prefManager.getUser();
        if (current != null) {
            editBinding.etEditName.setText(current.getName());
            editBinding.etEditPhone.setText(current.getPhone());
        }

        editBinding.btnCancelEdit.setOnClickListener(v -> dialog.dismiss());

        editBinding.btnSaveEdit.setOnClickListener(v -> {
            String newName = editBinding.etEditName.getText().toString().trim();
            String newPhone = editBinding.etEditPhone.getText().toString().trim();

            if (newName.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng không để trống thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (current != null) {
                current.setName(newName);
                current.setPhone(newPhone);
                prefManager.saveUser(current);
                dbHelper.updateUserProfile(current.getEmail(), newName, newPhone);

                Toast.makeText(requireContext(), "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                loadUserProfile();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserProfile();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
