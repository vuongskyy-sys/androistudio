-- =============================================================================
-- INT4211 · LẬP TRÌNH TRÊN CÁC THIẾT BỊ DI ĐỘNG - ĐỒ ÁN CUỐI KỲ
-- ĐỀ TÀI SỐ 22: NỀN TẢNG KẾT NỐI GIA SƯ VÀ PHỤ HUYNH (EDUCONNECT)
-- CƠ SỞ DỮ LIỆU POSTGRESQL (SUPABASE) - SCHEMA DEFINITION (≥ 8 BẢNG CÓ KHÓA NGOẠI)
-- =============================================================================

-- 1. BẢNG USERS (Tài khoản người dùng trong hệ thống)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('admin', 'parent', 'tutor')),
    avatar_url TEXT DEFAULT 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=200',
    balance NUMERIC(15, 2) DEFAULT 0.00 CHECK (balance >= 0),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. BẢNG TUTORS (Hồ sơ năng lực giảng dạy của gia sư)
CREATE TABLE IF NOT EXISTS tutors (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    university VARCHAR(200) NOT NULL,
    bio TEXT,
    experience_years NUMERIC(3, 1) DEFAULT 1.0,
    hourly_rate NUMERIC(12, 2) NOT NULL CHECK (hourly_rate > 0),
    rating_avg NUMERIC(2, 1) DEFAULT 5.0,
    total_reviews INTEGER DEFAULT 0,
    is_verified BOOLEAN DEFAULT TRUE,
    district VARCHAR(100) NOT NULL,
    teaching_mode VARCHAR(50) DEFAULT 'Tại nhà & Online',
    badge VARCHAR(100),
    achievements TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. BẢNG SUBJECTS (Danh mục môn học & Chuyên ngành đào tạo)
CREATE TABLE IF NOT EXISTS subjects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    category VARCHAR(50) NOT NULL CHECK (category IN ('math', 'tech', 'languages', 'sciences')),
    description TEXT
);

-- 4. BẢNG TUTOR_SUBJECTS (Môn học cụ thể do gia sư giảng dạy - Quan hệ N-N)
CREATE TABLE IF NOT EXISTS tutor_subjects (
    id SERIAL PRIMARY KEY,
    tutor_id INTEGER NOT NULL REFERENCES tutors(id) ON DELETE CASCADE,
    subject_id INTEGER NOT NULL REFERENCES subjects(id) ON DELETE CASCADE,
    grade_level VARCHAR(50) NOT NULL,
    hourly_rate_override NUMERIC(12, 2),
    UNIQUE (tutor_id, subject_id, grade_level)
);

-- 5. BẢNG BOOKINGS (Hợp đồng / Lượt đăng ký học của phụ huynh)
CREATE TABLE IF NOT EXISTS bookings (
    id SERIAL PRIMARY KEY,
    booking_code VARCHAR(50) UNIQUE NOT NULL,
    parent_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tutor_id INTEGER NOT NULL REFERENCES tutors(id) ON DELETE CASCADE,
    subject_id INTEGER NOT NULL REFERENCES subjects(id) ON DELETE RESTRICT,
    student_name VARCHAR(150) NOT NULL,
    student_grade VARCHAR(50) NOT NULL,
    learning_mode VARCHAR(50) NOT NULL,
    total_sessions INTEGER DEFAULT 1 CHECK (total_sessions > 0),
    status VARCHAR(30) DEFAULT 'pending' CHECK (status IN ('pending', 'accepted', 'rejected', 'in_progress', 'completed', 'cancelled')),
    note TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 6. BẢNG SESSIONS (TỪNG BUỔI HỌC CỤ THỂ — ĐIỂM NHẤN XÁC NHẬN HAI CHIỀU)
-- Buổi học chỉ hoàn thành (và tính tiền) khi CẢ tutor_confirmed VÀ parent_confirmed đều là TRUE
CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    booking_id INTEGER NOT NULL REFERENCES bookings(id) ON DELETE CASCADE,
    session_code VARCHAR(50) UNIQUE NOT NULL,
    session_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    session_rate NUMERIC(12, 2) NOT NULL CHECK (session_rate > 0),
    tutor_confirmed BOOLEAN DEFAULT FALSE,
    parent_confirmed BOOLEAN DEFAULT FALSE,
    tutor_confirmed_at TIMESTAMP WITH TIME ZONE,
    parent_confirmed_at TIMESTAMP WITH TIME ZONE,
    status VARCHAR(30) DEFAULT 'scheduled' CHECK (status IN ('scheduled', 'tutor_confirmed', 'parent_confirmed', 'completed', 'disputed', 'cancelled')),
    note TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 7. BẢNG TRANSACTIONS (GIAO DỊCH TÍNH TIỀN TỰ ĐỘNG THÔNG QUA TRANSACTION)
CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    transaction_code VARCHAR(50) UNIQUE NOT NULL,
    session_id INTEGER REFERENCES sessions(id) ON DELETE SET NULL,
    booking_id INTEGER REFERENCES bookings(id) ON DELETE SET NULL,
    payer_id INTEGER NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    recipient_id INTEGER NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    amount NUMERIC(15, 2) NOT NULL CHECK (amount > 0),
    transaction_type VARCHAR(30) NOT NULL CHECK (transaction_type IN ('tuition_release', 'deposit', 'refund', 'platform_fee')),
    status VARCHAR(20) DEFAULT 'completed' CHECK (status IN ('pending', 'completed', 'failed')),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 8. BẢNG REVIEWS (Đánh giá và phản hồi của phụ huynh sau buổi học)
CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    session_id INTEGER UNIQUE REFERENCES sessions(id) ON DELETE CASCADE,
    booking_id INTEGER NOT NULL REFERENCES bookings(id) ON DELETE CASCADE,
    parent_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tutor_id INTEGER NOT NULL REFERENCES tutors(id) ON DELETE CASCADE,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 9. BẢNG DISPUTES (Khiếu nại / Tranh chấp buổi học — Admin phân xử trên App Android)
CREATE TABLE IF NOT EXISTS disputes (
    id SERIAL PRIMARY KEY,
    session_id INTEGER NOT NULL REFERENCES sessions(id) ON DELETE CASCADE,
    reported_by INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    reason TEXT NOT NULL,
    admin_note TEXT,
    status VARCHAR(30) DEFAULT 'pending' CHECK (status IN ('pending', 'under_review', 'resolved_parent', 'resolved_tutor', 'dismissed')),
    resolved_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- TẠO CHỈ MỤC (INDEXES) TỐI ƯU TRUY VẤN
CREATE INDEX IF NOT EXISTS idx_sessions_booking_id ON sessions(booking_id);
CREATE INDEX IF NOT EXISTS idx_sessions_status ON sessions(status);
CREATE INDEX IF NOT EXISTS idx_bookings_parent_id ON bookings(parent_id);
CREATE INDEX IF NOT EXISTS idx_bookings_tutor_id ON bookings(tutor_id);
CREATE INDEX IF NOT EXISTS idx_tutors_category ON tutors(id);
CREATE INDEX IF NOT EXISTS idx_transactions_session_id ON transactions(session_id);
