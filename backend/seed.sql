-- =============================================================================
-- INT4211 · SEED DATA CHO HỆ THỐNG EDUCONNECT (POSTGRESQL)
-- =============================================================================

-- 1. NẠP USERS (Mật khẩu mặc định: 123456 -> bcrypt hash: $2a$10$w09dK1i9gD/qM9nQvD8I7OKX9kX7E9qQ1k1r1e1t1y1u1i1o1p)
-- Demo accounts: admin@educonnect.vn, parent@educonnect.vn, tutor@educonnect.vn
INSERT INTO users (id, email, password_hash, full_name, phone, role, avatar_url, balance) VALUES
(1, 'admin@educonnect.vn', '$2b$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Admin EduConnect', '1900 6868', 'admin', 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&q=80&w=200', 5000000.00),
(2, 'parent@educonnect.vn', '$2b$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Chị Mai Hương', '0908 123 456', 'parent', 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=200', 1200000.00),
(3, 'tutor@educonnect.vn', '$2b$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Cô Nguyễn Minh Trang', '0912 345 678', 'tutor', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=200', 750000.00),
(4, 'ducanh@educonnect.vn', '$2b$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Thầy Trần Đức Anh', '0938 888 999', 'tutor', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=200', 0.00),
(5, 'phuonglinh@educonnect.vn', '$2b$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'Cô Lê Hoàng Phương Linh', '0977 111 222', 'tutor', 'https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&q=80&w=200', 0.00)
ON CONFLICT (id) DO NOTHING;

-- 2. NẠP SUBJECTS
INSERT INTO subjects (id, name, code, category, description) VALUES
(1, 'Toán Học & Luyện Thi', 'MATH', 'math', 'Toán 10-12, Luyện thi THPT Quốc gia, Toán tư duy Olympic'),
(2, 'Lập Trình & AI', 'TECH', 'tech', 'Python, C++, Thuật toán và Tin học trẻ'),
(3, 'Ngoại Ngữ & IELTS', 'LANG', 'languages', 'IELTS 7.5+, Tiếng Anh giao tiếp và phản xạ tự nhiên'),
(4, 'Vật Lý & Khoa Học', 'PHYS', 'sciences', 'Vật lý 10-12, Thí nghiệm và ôn thi Đại học Khối A'),
(5, 'Hóa Học & Y Dược', 'CHEM', 'sciences', 'Hóa học THPT và ôn thi Đại học Khối B')
ON CONFLICT (id) DO NOTHING;

-- 3. NẠP TUTORS
INSERT INTO tutors (id, user_id, title, university, bio, experience_years, hourly_rate, rating_avg, total_reviews, is_verified, district, teaching_mode, badge, achievements) VALUES
(1, 3, 'Thạc sĩ Sư Phạm Toán', 'ĐH Sư Phạm TP.HCM', 'Thủ khoa đầu ra Sư phạm Toán 2021 với hơn 5 năm giảng dạy chuyên sâu khối 10-12. Phương pháp logic, cá nhân hóa 1-1.', 5.0, 250000.00, 4.9, 38, TRUE, 'Quận 1, TP.HCM', 'Tại nhà & Online', 'Thủ khoa Sư Phạm 2021', '• Giải Nhì Olympic Toán SV toàn quốc\n• Tác giả chuỗi bài giảng Hình không gian'),
(2, 4, 'Kỹ sư Khoa Học Máy Tính', 'ĐH Bách Khoa TP.HCM', 'Cựu học sinh Chuyên Tin Lê Hồng Phong. Có 4 năm kèm học sinh từ 9-18 tuổi làm quen với coding và thi Tin học trẻ.', 4.0, 320000.00, 5.0, 45, TRUE, 'TP. Thủ Đức, TP.HCM', 'Online & Tại nhà', 'Kỹ sư AI Bách Khoa', '• Huy chương Vàng Olympic Tin học SV\n• Kèm 8 học sinh đoạt giải Tin học Trẻ TP.HCM'),
(3, 5, 'Cử nhân Kinh Tế Đối Ngoại', 'ĐH Ngoại Thương (FTU2)', 'Sở hữu chứng chỉ IELTS 8.5 (Listening 9.0, Reading 9.0). Phương pháp Natural Immersion giúp học sinh phản xạ tự nhiên.', 4.0, 300000.00, 4.9, 29, TRUE, 'Quận Bình Thạnh, TP.HCM', 'Tại nhà & Online', 'IELTS 8.5 Overall', '• IELTS 8.5 Overall do IDP cấp\n• Chứng chỉ TESOL 120 Giờ')
ON CONFLICT (id) DO NOTHING;

-- 4. NẠP TUTOR_SUBJECTS
INSERT INTO tutor_subjects (id, tutor_id, subject_id, grade_level, hourly_rate_override) VALUES
(1, 1, 1, 'Lớp 12 & Luyện thi ĐH', 250000.00),
(2, 2, 2, 'THCS & Lập trình Python', 320000.00),
(3, 3, 3, 'IELTS 6.5 - 7.5+', 300000.00)
ON CONFLICT (id) DO NOTHING;

-- 5. NẠP BOOKINGS
INSERT INTO bookings (id, booking_code, parent_id, tutor_id, subject_id, student_name, student_grade, learning_mode, total_sessions, status, note) VALUES
(1, 'BK-2026-001', 2, 1, 1, 'Bé Đức Trí', 'Lớp 12 (Khối A1)', 'Tại nhà', 5, 'in_progress', 'Củng cố phần Đạo hàm và Khảo sát hàm số'),
(2, 'BK-2026-002', 2, 2, 2, 'Bé Minh Quân', 'Lớp 10', 'Online', 3, 'accepted', 'Làm quen với cú pháp Python cơ bản')
ON CONFLICT (id) DO NOTHING;

-- 6. NẠP SESSIONS (MINH HỌA CƠ CHẾ XÁC NHẬN HAI CHIỀU)
-- Buổi 1: ĐÃ HOÀN THÀNH (Cả Gia sư và Phụ huynh đều đã xác nhận -> status = completed)
INSERT INTO sessions (id, booking_id, session_code, session_date, start_time, end_time, session_rate, tutor_confirmed, parent_confirmed, tutor_confirmed_at, parent_confirmed_at, status, note) VALUES
(1, 1, 'SS-2026-101', '2026-11-01', '19:00:00', '21:00:00', 250000.00, TRUE, TRUE, '2026-11-01 21:05:00+07', '2026-11-01 21:15:00+07', 'completed', 'Hoàn thành chuyên đề Khảo sát hàm phân thức bậc 1 trên bậc 1.')
ON CONFLICT (id) DO NOTHING;

-- Buổi 2: ĐANG CHỜ PHỤ HUYNH XÁC NHẬN (Gia sư đã bấm xác nhận dạy xong, Phụ huynh chưa bấm -> status = tutor_confirmed)
INSERT INTO sessions (id, booking_id, session_code, session_date, start_time, end_time, session_rate, tutor_confirmed, parent_confirmed, tutor_confirmed_at, parent_confirmed_at, status, note) VALUES
(2, 1, 'SS-2026-102', '2026-11-05', '19:00:00', '21:00:00', 250000.00, TRUE, FALSE, '2026-11-05 21:02:00+07', NULL, 'tutor_confirmed', 'Đã dạy xong bài Cực trị hình học không gian. Đang đợi phụ huynh xác nhận để giải ngân học phí.')
ON CONFLICT (id) DO NOTHING;

-- Buổi 3: MỚI LÊN LỊCH (Chưa ai bấm xác nhận -> status = scheduled)
INSERT INTO sessions (id, booking_id, session_code, session_date, start_time, end_time, session_rate, tutor_confirmed, parent_confirmed, tutor_confirmed_at, parent_confirmed_at, status, note) VALUES
(3, 1, 'SS-2026-103', '2026-11-12', '19:00:00', '21:00:00', 250000.00, FALSE, FALSE, NULL, NULL, 'scheduled', 'Buổi học tiếp theo theo thời khóa biểu.')
ON CONFLICT (id) DO NOTHING;

-- 7. NẠP TRANSACTIONS (KẾT QUẢ TÍNH TIỀN CỦA BUỔI 1 KHI ĐƯỢC XÁC NHẬN HAI CHIỀU)
INSERT INTO transactions (id, transaction_code, session_id, booking_id, payer_id, recipient_id, amount, transaction_type, status) VALUES
(1, 'TX-2026-901', 1, 1, 2, 3, 250000.00, 'tuition_release', 'completed')
ON CONFLICT (id) DO NOTHING;

-- 8. NẠP REVIEWS
INSERT INTO reviews (id, session_id, booking_id, parent_id, tutor_id, rating, comment) VALUES
(1, 1, 1, 2, 1, 5, 'Cô Trang giảng rất tận tâm và dễ hiểu, bé Trí tiếp thu rất nhanh!')
ON CONFLICT (id) DO NOTHING;

-- Cập nhật sequence các bảng
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('tutors_id_seq', (SELECT MAX(id) FROM tutors));
SELECT setval('subjects_id_seq', (SELECT MAX(id) FROM subjects));
SELECT setval('tutor_subjects_id_seq', (SELECT MAX(id) FROM tutor_subjects));
SELECT setval('bookings_id_seq', (SELECT MAX(id) FROM bookings));
SELECT setval('sessions_id_seq', (SELECT MAX(id) FROM sessions));
SELECT setval('transactions_id_seq', (SELECT MAX(id) FROM transactions));
SELECT setval('reviews_id_seq', (SELECT MAX(id) FROM reviews));
