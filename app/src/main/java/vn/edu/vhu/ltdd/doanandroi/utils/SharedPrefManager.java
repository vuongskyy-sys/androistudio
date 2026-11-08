package vn.edu.vhu.ltdd.doanandroi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.vhu.ltdd.doanandroi.models.User;

public class SharedPrefManager {
    private static final String PREF_NAME = "educonnect_session_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_USER_AVATAR = "user_avatar";

    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_ROLE, user.getRole());
        editor.putString(KEY_USER_AVATAR, user.getAvatar());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public User getUser() {
        if (!isLoggedIn()) return null;
        User user = new User();
        user.setId(sharedPreferences.getString(KEY_USER_ID, ""));
        user.setName(sharedPreferences.getString(KEY_USER_NAME, ""));
        user.setEmail(sharedPreferences.getString(KEY_USER_EMAIL, ""));
        user.setPhone(sharedPreferences.getString(KEY_USER_PHONE, ""));
        user.setRole(sharedPreferences.getString(KEY_USER_ROLE, "parent"));
        user.setAvatar(sharedPreferences.getString(KEY_USER_AVATAR, ""));
        return user;
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
