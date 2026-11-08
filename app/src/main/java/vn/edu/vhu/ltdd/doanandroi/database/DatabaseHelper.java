package vn.edu.vhu.ltdd.doanandroi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.edu.vhu.ltdd.doanandroi.models.Booking;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;
import vn.edu.vhu.ltdd.doanandroi.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "educonnect.db";
    private static final int DATABASE_VERSION = 1;

    // Table Users
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_ROLE = "role";
    private static final String COL_USER_AVATAR = "avatar";

    // Table Tutors
    private static final String TABLE_TUTORS = "tutors";
    private static final String COL_TUTOR_ID = "id";
    private static final String COL_TUTOR_NAME = "name";
    private static final String COL_TUTOR_AVATAR = "avatar";
    private static final String COL_TUTOR_TITLE = "title";
    private static final String COL_TUTOR_UNIVERSITY = "university";
    private static final String COL_TUTOR_SUBJECTS = "subjects";
    private static final String COL_TUTOR_CATEGORY = "category";
    private static final String COL_TUTOR_RATE = "hourly_rate";
    private static final String COL_TUTOR_RATING = "rating";
    private static final String COL_TUTOR_REVIEWS = "reviews_count";
    private static final String COL_TUTOR_DISTRICT = "district";
    private static final String COL_TUTOR_MODE = "mode";
    private static final String COL_TUTOR_BADGE = "badge";
    private static final String COL_TUTOR_BIO = "bio";
    private static final String COL_TUTOR_EXP = "experience";
    private static final String COL_TUTOR_ACHIEVEMENTS = "achievements";
    private static final String COL_TUTOR_VERIFIED = "verified";

    // Table Bookings
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COL_BOOKING_ID = "id";
    private static final String COL_BOOKING_TUTOR_ID = "tutor_id";
    private static final String COL_BOOKING_TUTOR_NAME = "tutor_name";
    private static final String COL_BOOKING_TUTOR_AVATAR = "tutor_avatar";
    private static final String COL_BOOKING_PARENT_NAME = "parent_name";
    private static final String COL_BOOKING_PHONE = "phone";
    private static final String COL_BOOKING_STUDENT_NAME = "student_name";
    private static final String COL_BOOKING_STUDENT_GRADE = "student_grade";
    private static final String COL_BOOKING_SUBJECT = "subject";
    private static final String COL_BOOKING_MODE = "mode";
    private static final String COL_BOOKING_DATE = "date";
    private static final String COL_BOOKING_TIMESLOT = "time_slot";
    private static final String COL_BOOKING_NOTE = "note";
    private static final String COL_BOOKING_STATUS = "status";

    // Table Favorites
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COL_FAV_TUTOR_ID = "tutor_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " ("
                + COL_USER_ID + " TEXT PRIMARY KEY, "
                + COL_USER_NAME + " TEXT, "
                + COL_USER_EMAIL + " TEXT UNIQUE, "
                + COL_USER_PHONE + " TEXT, "
                + COL_USER_PASSWORD + " TEXT, "
                + COL_USER_ROLE + " TEXT, "
                + COL_USER_AVATAR + " TEXT)";
        db.execSQL(createUsersTable);

        // Create Tutors Table
        String createTutorsTable = "CREATE TABLE " + TABLE_TUTORS + " ("
                + COL_TUTOR_ID + " TEXT PRIMARY KEY, "
                + COL_TUTOR_NAME + " TEXT, "
                + COL_TUTOR_AVATAR + " TEXT, "
                + COL_TUTOR_TITLE + " TEXT, "
                + COL_TUTOR_UNIVERSITY + " TEXT, "
                + COL_TUTOR_SUBJECTS + " TEXT, "
                + COL_TUTOR_CATEGORY + " TEXT, "
                + COL_TUTOR_RATE + " REAL, "
                + COL_TUTOR_RATING + " REAL, "
                + COL_TUTOR_REVIEWS + " INTEGER, "
                + COL_TUTOR_DISTRICT + " TEXT, "
                + COL_TUTOR_MODE + " TEXT, "
                + COL_TUTOR_BADGE + " TEXT, "
                + COL_TUTOR_BIO + " TEXT, "
                + COL_TUTOR_EXP + " TEXT, "
                + COL_TUTOR_ACHIEVEMENTS + " TEXT, "
                + COL_TUTOR_VERIFIED + " INTEGER)";
        db.execSQL(createTutorsTable);

        // Create Bookings Table
        String createBookingsTable = "CREATE TABLE " + TABLE_BOOKINGS + " ("
                + COL_BOOKING_ID + " TEXT PRIMARY KEY, "
                + COL_BOOKING_TUTOR_ID + " TEXT, "
                + COL_BOOKING_TUTOR_NAME + " TEXT, "
                + COL_BOOKING_TUTOR_AVATAR + " TEXT, "
                + COL_BOOKING_PARENT_NAME + " TEXT, "
                + COL_BOOKING_PHONE + " TEXT, "
                + COL_BOOKING_STUDENT_NAME + " TEXT, "
                + COL_BOOKING_STUDENT_GRADE + " TEXT, "
                + COL_BOOKING_SUBJECT + " TEXT, "
                + COL_BOOKING_MODE + " TEXT, "
                + COL_BOOKING_DATE + " TEXT, "
                + COL_BOOKING_TIMESLOT + " TEXT, "
                + COL_BOOKING_NOTE + " TEXT, "
                + COL_BOOKING_STATUS + " TEXT)";
        db.execSQL(createBookingsTable);

        // Create Favorites Table
        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + " ("
                + COL_FAV_TUTOR_ID + " TEXT PRIMARY KEY)";
        db.execSQL(createFavoritesTable);

        // Seed initial data
        seedInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    private void seedInitialData(SQLiteDatabase db) {
        // 1. Seed Users
        insertUserDirect(db, new User("usr-01", "Chị Mai Hương", "parent@educonnect.vn", "0908 123 456", "123456", "parent", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=200"));
        insertUserDirect(db, new User("usr-02", "Cô Nguyễn Minh Trang", "tutor@educonnect.vn", "0912 345 678", "123456", "tutor", "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=200"));
        insertUserDirect(db, new User("usr-03", "Admin EduConnect", "admin@educonnect.vn", "1900 6868", "123456", "admin", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&q=80&w=200"));

        // 2. Seed 7 Verified Tutors
        insertTutorDirect(db, new Tutor(
                "tut-01",
                "Nguyễn Minh Trang",
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=400",
                "Thạc sĩ Sư Phạm Toán — ĐH Sư Phạm TP.HCM",
                "ĐH Sư Phạm TP.HCM",
                "Toán 12, Luyện thi ĐH, Toán Olympic, Toán 10-11",
                "math",
                250000,
                4.9f,
                38,
                "Quận 1, TP.HCM",
                "Tại nhà & Online",
                "Thủ khoa Sư Phạm 2021",
                "Thủ khoa đầu ra Sư phạm Toán 2021 với hơn 5 năm giảng dạy chuyên sâu khối 10-12. Phương pháp sư phạm logic, bám sát bản chất toán học, cá nhân hóa lộ trình 1-1 giúp hơn 120 học sinh đạt 8.5+ THPT Quốc gia.",
                "5 năm luyện thi chuyên sâu",
                "• Giải Nhì Olympic Toán sinh viên toàn quốc 2020\n• Chứng chỉ Sư phạm loại Xuất sắc\n• Tác giả chuỗi video Chinh phục Hình không gian 12",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-02",
                "Trần Đức Anh",
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=400",
                "Kỹ sư Khoa Học Máy Tính — ĐH Bách Khoa TP.HCM",
                "ĐH Bách Khoa TP.HCM",
                "Lập trình Python, Tin học trẻ, C++ Nâng cao, Toán tư duy",
                "tech",
                320000,
                5.0f,
                45,
                "TP. Thủ Đức, TP.HCM",
                "Online & Tại nhà",
                "Kỹ sư AI Bách Khoa",
                "Cựu học sinh Chuyên Tin Lê Hồng Phong, tốt nghiệp loại Giỏi ĐH Bách Khoa TP.HCM. Có 4 năm kèm học sinh từ 9-18 tuổi làm quen với coding, thuật toán và thi Tin học trẻ.",
                "4 năm giảng dạy Coding & AI",
                "• Huy chương Vàng Olympic Tin học sinh viên\n• Tác giả giáo trình 'Python cho lứa tuổi THCS'\n• Kèm 8 học sinh đoạt giải Tin học Trẻ TP.HCM",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-03",
                "Lê Hoàng Phương Linh",
                "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&q=80&w=400",
                "Cử nhân Kinh Tế Đối Ngoại — ĐH Ngoại Thương TP.HCM",
                "ĐH Ngoại Thương (FTU2)",
                "IELTS 7.5+, Tiếng Anh Giao Tiếp, Ngữ Pháp Chuyên",
                "languages",
                300000,
                4.9f,
                29,
                "Quận Bình Thạnh, TP.HCM",
                "Tại nhà & Online",
                "IELTS 8.5 Overall",
                "Sở hữu chứng chỉ IELTS 8.5 (Listening 9.0, Reading 9.0). Phương pháp Natural Immersion giúp học sinh phản xạ tự nhiên chuẩn Anh - Mỹ và bứt phá điểm số Writing.",
                "4 năm luyện thi chứng chỉ quốc tế",
                "• IELTS 8.5 Overall (IDP cấp 2024)\n• Chứng chỉ Giảng dạy Quốc tế TESOL 120 Giờ\n• Diễn giả Workshop 'Bí quyết bứt phá IELTS Writing Task 2'",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-04",
                "Vũ Thanh Tùng",
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=400",
                "Cử nhân Vật Lý Tiên Tiến — ĐH Khoa Học Tự Nhiên TP.HCM",
                "ĐH Khoa Học Tự Nhiên (ĐHQG)",
                "Vật Lý 10-12, Luyện thi ĐH Khối A, Vật Lý THCS",
                "sciences",
                220000,
                4.8f,
                22,
                "Quận 5, TP.HCM",
                "Tại nhà & Online",
                "Top 1 Khoa Vật Lý",
                "Chương trình Tiên tiến Vật lý chuẩn quốc tế. Phong cách dạy sinh động, trực quan hóa hiện tượng vật lý phức tạp (Dao động điều hòa, Điện xoay chiều) thành sơ đồ dễ nhớ.",
                "3.5 năm luyện thi Đại học",
                "• Giải Ba NCKH Sinh viên Cấp Thành phố môn Vật Lý\n• Học bổng Tài năng ĐHQG 3 năm liên tiếp\n• Biên soạn sơ đồ tư duy 24 chuyên đề Vật lý 12",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-05",
                "Phạm Quỳnh Nga",
                "https://images.unsplash.com/photo-1594744803329-e58b31de8bf5?auto=format&fit=crop&q=80&w=400",
                "Bác Sĩ Đa Khoa Nội Trú — ĐH Y Dược TP.HCM",
                "ĐH Y Dược TP.HCM",
                "Hóa Học 10-12, Sinh Học 10-12, Luyện thi Khối B",
                "sciences",
                280000,
                5.0f,
                31,
                "Quận 10, TP.HCM",
                "Tại nhà & Online",
                "Thủ khoa Khối B (29.25đ)",
                "Thủ khoa đầu vào Khối B ĐH Y Dược TP.HCM 2019 với 29.25 điểm (Toán 9.5, Hóa 9.75, Sinh 10.0). Với 6 năm kinh nghiệm, cô Nga kèm học sinh định hướng thi Y Dược.",
                "6 năm luyện thi Y Dược",
                "• Bác sĩ Nội trú Chuyên ngành Cấp cứu\n• Tác giả bộ 500 câu bẫy Hóa học vô cơ & hữu cơ\n• 45+ học sinh đỗ ĐH Y Dược TP.HCM",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-06",
                "Hoàng Nhật Minh",
                "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&q=80&w=400",
                "Giảng viên Thỉnh giảng Sư Phạm — ĐH Sài Gòn",
                "ĐH Sài Gòn",
                "Tiếng Anh Tiểu học, Luyện thi vào 10 Chuyên, Phát âm Phonics",
                "languages",
                210000,
                4.8f,
                19,
                "Quận Tân Bình, TP.HCM",
                "Tại nhà & Online",
                "Sư phạm Tiếng Anh Xuất sắc",
                "Giảng viên thỉnh giảng tâm huyết với phương pháp Gamification (học qua trò chơi tương tác), khơi gợi niềm yêu thích tiếng Anh tự nhiên cho học sinh tiểu học và cấp 2.",
                "4 năm giảng dạy lứa tuổi 7-15",
                "• Chứng chỉ Cambridge CELTA Hạng A (Pass A)\n• Giải Nhất Nghiệp vụ Sư phạm ĐH Sài Gòn\n• Đào tạo 30+ học sinh thi đỗ chuyên Anh Lê Hồng Phong",
                true
        ));

        insertTutorDirect(db, new Tutor(
                "tut-07",
                "Đặng Bảo Châu",
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=400",
                "Kỹ sư Khoa Học Dữ Liệu & AI — ĐH Quốc Tế (ĐHQG)",
                "ĐH Quốc Tế (IU - ĐHQG)",
                "Toán Cao Cấp, Lập trình Python, Xác Suất Thống Kê, IELTS 7.0",
                "tech",
                350000,
                4.9f,
                27,
                "Quận 7, TP.HCM",
                "Online & Tại nhà",
                "Chuyên gia Data Science",
                "Thủ khoa ngành Khoa Học Dữ Liệu trường ĐH Quốc Tế. Chuyên kèm sinh viên đại học môn Toán cao cấp, Giải tích, Đại số tuyến tính, Xác suất thống kê và Machine Learning.",
                "4 năm giảng dạy Toán ĐH & SV Quốc tế",
                "• Học bổng Toàn phần Hiệu trưởng ĐH Quốc Tế\n• Công bố bài báo khoa học Hội nghị IEEE 2024\n• Cố vấn Hackathon ĐHQG 2025",
                true
        ));

        // 3. Seed Initial Bookings
        insertBookingDirect(db, new Booking(
                "BK-2026-8801",
                "tut-01",
                "Nguyễn Minh Trang",
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=400",
                "Chị Mai Hương",
                "0908 123 456",
                "Bé Đức Trí",
                "Lớp 12 (Khối A1)",
                "Toán 12",
                "Học Trực tiếp tại nhà",
                "2026-10-30",
                "Tối (19:00 - 21:00)",
                "Kiểm tra kiến thức phần Đạo hàm và Khảo sát đồ thị hàm số.",
                "Confirmed"
        ));

        insertBookingDirect(db, new Booking(
                "BK-2026-8802",
                "tut-02",
                "Trần Đức Anh",
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=400",
                "Chị Mai Hương",
                "0908 123 456",
                "Bé Minh Quân",
                "Lớp 10",
                "Lập trình Python",
                "Học Online qua Google Meet",
                "2026-10-31",
                "Chiều (15:00 - 17:00)",
                "Bé muốn học làm quen với cú pháp Python và lập trình hướng đối tượng cơ bản.",
                "Pending"
        ));

        // 4. Seed initial favorite
        ContentValues fav = new ContentValues();
        fav.put(COL_FAV_TUTOR_ID, "tut-01");
        db.insert(TABLE_FAVORITES, null, fav);
    }

    private void insertUserDirect(SQLiteDatabase db, User user) {
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, user.getId());
        cv.put(COL_USER_NAME, user.getName());
        cv.put(COL_USER_EMAIL, user.getEmail());
        cv.put(COL_USER_PHONE, user.getPhone());
        cv.put(COL_USER_PASSWORD, user.getPassword());
        cv.put(COL_USER_ROLE, user.getRole());
        cv.put(COL_USER_AVATAR, user.getAvatar());
        db.insert(TABLE_USERS, null, cv);
    }

    private void insertTutorDirect(SQLiteDatabase db, Tutor tutor) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TUTOR_ID, tutor.getId());
        cv.put(COL_TUTOR_NAME, tutor.getName());
        cv.put(COL_TUTOR_AVATAR, tutor.getAvatar());
        cv.put(COL_TUTOR_TITLE, tutor.getTitle());
        cv.put(COL_TUTOR_UNIVERSITY, tutor.getUniversity());
        cv.put(COL_TUTOR_SUBJECTS, tutor.getSubjects());
        cv.put(COL_TUTOR_CATEGORY, tutor.getCategory());
        cv.put(COL_TUTOR_RATE, tutor.getHourlyRate());
        cv.put(COL_TUTOR_RATING, tutor.getRating());
        cv.put(COL_TUTOR_REVIEWS, tutor.getReviewsCount());
        cv.put(COL_TUTOR_DISTRICT, tutor.getDistrict());
        cv.put(COL_TUTOR_MODE, tutor.getMode());
        cv.put(COL_TUTOR_BADGE, tutor.getBadge());
        cv.put(COL_TUTOR_BIO, tutor.getBio());
        cv.put(COL_TUTOR_EXP, tutor.getExperience());
        cv.put(COL_TUTOR_ACHIEVEMENTS, tutor.getAchievements());
        cv.put(COL_TUTOR_VERIFIED, tutor.isVerified() ? 1 : 0);
        db.insert(TABLE_TUTORS, null, cv);
    }

    private void insertBookingDirect(SQLiteDatabase db, Booking booking) {
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOKING_ID, booking.getId());
        cv.put(COL_BOOKING_TUTOR_ID, booking.getTutorId());
        cv.put(COL_BOOKING_TUTOR_NAME, booking.getTutorName());
        cv.put(COL_BOOKING_TUTOR_AVATAR, booking.getTutorAvatar());
        cv.put(COL_BOOKING_PARENT_NAME, booking.getParentName());
        cv.put(COL_BOOKING_PHONE, booking.getPhone());
        cv.put(COL_BOOKING_STUDENT_NAME, booking.getStudentName());
        cv.put(COL_BOOKING_STUDENT_GRADE, booking.getStudentGrade());
        cv.put(COL_BOOKING_SUBJECT, booking.getSubject());
        cv.put(COL_BOOKING_MODE, booking.getMode());
        cv.put(COL_BOOKING_DATE, booking.getDate());
        cv.put(COL_BOOKING_TIMESLOT, booking.getTimeSlot());
        cv.put(COL_BOOKING_NOTE, booking.getNote());
        cv.put(COL_BOOKING_STATUS, booking.getStatus());
        db.insert(TABLE_BOOKINGS, null, cv);
    }

    // ==========================================
    // Public CRUD Operations
    // ==========================================

    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, "usr-" + System.currentTimeMillis());
        cv.put(COL_USER_NAME, user.getName());
        cv.put(COL_USER_EMAIL, user.getEmail());
        cv.put(COL_USER_PHONE, user.getPhone());
        cv.put(COL_USER_PASSWORD, user.getPassword());
        cv.put(COL_USER_ROLE, user.getRole());
        cv.put(COL_USER_AVATAR, "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&q=80&w=200");
        long result = db.insert(TABLE_USERS, null, cv);
        return result != -1;
    }

    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_EMAIL + " = ? AND " + COL_USER_PASSWORD + " = ?", new String[]{email, password});
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ROLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_AVATAR))
            );
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_EMAIL + " = ?", new String[]{email});
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return exists;
    }

    public void updateUserProfile(String email, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_NAME, name);
        cv.put(COL_USER_PHONE, phone);
        db.update(TABLE_USERS, cv, COL_USER_EMAIL + " = ?", new String[]{email});
    }

    public List<Tutor> getAllTutors() {
        return searchTutors(null, "all");
    }

    public List<Tutor> searchTutors(String query, String category) {
        List<Tutor> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_TUTORS + " WHERE 1=1");
        List<String> args = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            sql.append(" AND (" + COL_TUTOR_NAME + " LIKE ? OR " + COL_TUTOR_SUBJECTS + " LIKE ? OR " + COL_TUTOR_UNIVERSITY + " LIKE ?)");
            String q = "%" + query.trim() + "%";
            args.add(q);
            args.add(q);
            args.add(q);
        }

        if (category != null && !category.equalsIgnoreCase("all")) {
            sql.append(" AND " + COL_TUTOR_CATEGORY + " = ?");
            args.add(category);
        }

        sql.append(" ORDER BY " + COL_TUTOR_RATING + " DESC");

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_AVATAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_UNIVERSITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_SUBJECTS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_CATEGORY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TUTOR_RATE)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(COL_TUTOR_RATING)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_TUTOR_REVIEWS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_DISTRICT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_MODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_BADGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_BIO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_EXP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_ACHIEVEMENTS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_TUTOR_VERIFIED)) == 1
                );
                list.add(tutor);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public Tutor getTutorById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TUTORS + " WHERE " + COL_TUTOR_ID + " = ?", new String[]{id});
        if (cursor != null && cursor.moveToFirst()) {
            Tutor tutor = new Tutor(
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_AVATAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_UNIVERSITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_SUBJECTS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_CATEGORY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TUTOR_RATE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(COL_TUTOR_RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_TUTOR_REVIEWS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_DISTRICT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_MODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_BADGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_BIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_EXP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TUTOR_ACHIEVEMENTS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_TUTOR_VERIFIED)) == 1
            );
            cursor.close();
            return tutor;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean insertBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOKING_ID, booking.getId());
        cv.put(COL_BOOKING_TUTOR_ID, booking.getTutorId());
        cv.put(COL_BOOKING_TUTOR_NAME, booking.getTutorName());
        cv.put(COL_BOOKING_TUTOR_AVATAR, booking.getTutorAvatar());
        cv.put(COL_BOOKING_PARENT_NAME, booking.getParentName());
        cv.put(COL_BOOKING_PHONE, booking.getPhone());
        cv.put(COL_BOOKING_STUDENT_NAME, booking.getStudentName());
        cv.put(COL_BOOKING_STUDENT_GRADE, booking.getStudentGrade());
        cv.put(COL_BOOKING_SUBJECT, booking.getSubject());
        cv.put(COL_BOOKING_MODE, booking.getMode());
        cv.put(COL_BOOKING_DATE, booking.getDate());
        cv.put(COL_BOOKING_TIMESLOT, booking.getTimeSlot());
        cv.put(COL_BOOKING_NOTE, booking.getNote());
        cv.put(COL_BOOKING_STATUS, booking.getStatus());
        return db.insert(TABLE_BOOKINGS, null, cv) != -1;
    }

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " ORDER BY " + COL_BOOKING_DATE + " ASC", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Booking booking = new Booking(
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_TUTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_TUTOR_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_TUTOR_AVATAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_PARENT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_STUDENT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_STUDENT_GRADE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_SUBJECT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_MODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_TIMESLOT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_NOTE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_BOOKING_STATUS))
                );
                list.add(booking);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public boolean cancelBooking(String bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOKINGS, COL_BOOKING_ID + " = ?", new String[]{bookingId}) > 0;
    }

    public boolean toggleFavorite(String tutorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isFavorite(tutorId)) {
            db.delete(TABLE_FAVORITES, COL_FAV_TUTOR_ID + " = ?", new String[]{tutorId});
            return false;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COL_FAV_TUTOR_ID, tutorId);
            db.insert(TABLE_FAVORITES, null, cv);
            return true;
        }
    }

    public boolean isFavorite(String tutorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES + " WHERE " + COL_FAV_TUTOR_ID + " = ?", new String[]{tutorId});
        boolean fav = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return fav;
    }

    public int getFavoriteCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FAVORITES, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getBookingsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKINGS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }
}
