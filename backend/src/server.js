const express = require('express');
const cors = require('cors');
const { Pool } = require('pg');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 4000;
const JWT_SECRET = process.env.JWT_SECRET || 'educonnect_super_secret_jwt_key_2026_int4211';

// Cấu hình Database Pool kết nối Supabase qua Connection Pooler cổng 5432
const pool = new Pool({
    connectionString: process.env.DATABASE_URL || 'postgresql://postgres:postgres@localhost:5432/educonnect',
    ssl: process.env.DATABASE_URL && process.env.DATABASE_URL.includes('supabase.com') ? { rejectUnauthorized: false } : false
});

app.use(cors());
app.use(express.json());

// Middleware xác thực JWT Token
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    if (!token) return res.status(401).json({ success: false, message: 'Yêu cầu token xác thực (Bearer Token)' });

    jwt.verify(token, JWT_SECRET, (err, user) => {
        if (err) return res.status(403).json({ success: false, message: 'Token không hợp lệ hoặc đã hết hạn' });
        req.user = user;
        next();
    });
}

// Middleware phân quyền
function requireRole(...roles) {
    return (req, res, next) => {
        if (!req.user || !roles.includes(req.user.role)) {
            return res.status(403).json({ success: false, message: 'Bạn không có quyền truy cập chức năng này' });
        }
        next();
    };
}

// =============================================================================
// 1. AUTH ROUTES
// =============================================================================
app.post('/api/auth/login', async (req, res) => {
    try {
        const { email, password } = req.body;
        if (!email || !password) return res.status(400).json({ success: false, message: 'Vui lòng nhập email và mật khẩu' });

        const result = await pool.query('SELECT * FROM users WHERE email = $1', [email]);
        if (result.rows.length === 0) return res.status(401).json({ success: false, message: 'Email hoặc mật khẩu không đúng' });

        const user = result.rows[0];
        const isMatch = await bcrypt.compare(password, user.password_hash);
        // Hỗ trợ kiểm thử demo cho mật khẩu 123456
        if (!isMatch && password !== '123456') {
            return res.status(401).json({ success: false, message: 'Email hoặc mật khẩu không đúng' });
        }

        const token = jwt.sign(
            { id: user.id, email: user.email, role: user.role, full_name: user.full_name },
            JWT_SECRET,
            { expiresIn: '7d' }
        );

        res.json({
            success: true,
            message: 'Đăng nhập thành công',
            token,
            user: {
                id: user.id,
                email: user.email,
                full_name: user.full_name,
                phone: user.phone,
                role: user.role,
                avatar_url: user.avatar_url,
                balance: user.balance
            }
        });
    } catch (err) {
        console.error('Login error:', err);
        res.status(500).json({ success: false, message: 'Lỗi máy chủ', error: err.message });
    }
});

app.post('/api/auth/register', async (req, res) => {
    try {
        const { email, password, full_name, phone, role } = req.body;
        if (!email || !password || !full_name || !phone) {
            return res.status(400).json({ success: false, message: 'Vui lòng điền đầy đủ thông tin bắt buộc' });
        }
        const userRole = role === 'tutor' ? 'tutor' : 'parent';
        const salt = await bcrypt.genSalt(10);
        const hash = await bcrypt.hash(password, salt);

        const result = await pool.query(
            'INSERT INTO users (email, password_hash, full_name, phone, role) VALUES ($1, $2, $3, $4, $5) RETURNING id, email, full_name, phone, role, avatar_url, balance',
            [email, hash, full_name, phone, userRole]
        );
        const newUser = result.rows[0];
        const token = jwt.sign(
            { id: newUser.id, email: newUser.email, role: newUser.role, full_name: newUser.full_name },
            JWT_SECRET,
            { expiresIn: '7d' }
        );
        res.status(201).json({ success: true, message: 'Đăng ký tài khoản thành công', token, user: newUser });
    } catch (err) {
        if (err.code === '23505') return res.status(400).json({ success: false, message: 'Email này đã được sử dụng' });
        res.status(500).json({ success: false, message: 'Lỗi máy chủ', error: err.message });
    }
});

// =============================================================================
// 2. TUTORS ROUTES (App Client & App Admin dùng chung)
// =============================================================================
app.get('/api/tutors', async (req, res) => {
    try {
        const { category, search } = req.query;
        let query = `
            SELECT t.*, u.full_name, u.avatar_url, u.email, u.phone
            FROM tutors t
            JOIN users u ON t.user_id = u.id
            WHERE 1=1
        `;
        const params = [];
        if (search) {
            params.push(`%${search}%`);
            query += ` AND (u.full_name ILIKE $${params.length} OR t.title ILIKE $${params.length} OR t.university ILIKE $${params.length})`;
        }
        query += ' ORDER BY t.rating_avg DESC, t.total_reviews DESC';
        const result = await pool.query(query, params);
        res.json({ success: true, count: result.rows.length, data: result.rows });
    } catch (err) {
        res.status(500).json({ success: false, message: 'Lỗi tải danh sách gia sư', error: err.message });
    }
});

app.get('/api/tutors/:id', async (req, res) => {
    try {
        const result = await pool.query(`
            SELECT t.*, u.full_name, u.avatar_url, u.email, u.phone
            FROM tutors t
            JOIN users u ON t.user_id = u.id
            WHERE t.id = $1
        `, [req.params.id]);
        if (result.rows.length === 0) return res.status(404).json({ success: false, message: 'Không tìm thấy gia sư' });
        res.json({ success: true, data: result.rows[0] });
    } catch (err) {
        res.status(500).json({ success: false, message: 'Lỗi máy chủ', error: err.message });
    }
});

// =============================================================================
// 3. SESSIONS & ĐIỂM NHẤN KỸ THUẬT: XÁC NHẬN HAI CHIỀU QUA DATABASE TRANSACTION
// =============================================================================

// Lấy danh sách buổi học của người dùng hiện tại (Phụ huynh hoặc Gia sư)
app.get('/api/sessions', authenticateToken, async (req, res) => {
    try {
        const userId = req.user.id;
        const role = req.user.role;
        let query = `
            SELECT s.*, b.booking_code, b.student_name, b.student_grade, b.learning_mode,
                   u_parent.full_name AS parent_name, u_parent.phone AS parent_phone,
                   u_tutor.full_name AS tutor_name, u_tutor.avatar_url AS tutor_avatar,
                   t.title AS tutor_title, sub.name AS subject_name
            FROM sessions s
            JOIN bookings b ON s.booking_id = b.id
            JOIN users u_parent ON b.parent_id = u_parent.id
            JOIN tutors t ON b.tutor_id = t.id
            JOIN users u_tutor ON t.user_id = u_tutor.id
            JOIN subjects sub ON b.subject_id = sub.id
            WHERE 1=1
        `;
        const params = [];
        if (role === 'parent') {
            params.push(userId);
            query += ` AND b.parent_id = $${params.length}`;
        } else if (role === 'tutor') {
            params.push(userId);
            query += ` AND t.user_id = $${params.length}`;
        }
        query += ' ORDER BY s.session_date DESC, s.start_time DESC';
        const result = await pool.query(query, params);
        res.json({ success: true, count: result.rows.length, data: result.rows });
    } catch (err) {
        res.status(500).json({ success: false, message: 'Lỗi tải danh sách buổi học', error: err.message });
    }
});

/**
 * ĐIỂM NHẤN KỸ THUẬT ĐỀ 22: API XÁC NHẬN HAI CHIỀU CHO BUỔI HỌC
 * POST /api/sessions/:id/confirm
 * Một buổi chỉ được tính (và tính tiền) khi CẢ GIA SƯ LẪN PHỤ HUYNH ĐỀU XÁC NHẬN!
 * Quy trình sử dụng PostgreSQL Transaction (BEGIN ... COMMIT ... ROLLBACK)
 */
app.post('/api/sessions/:id/confirm', authenticateToken, async (req, res) => {
    const client = await pool.connect();
    try {
        const sessionId = req.params.id;
        const currentUserId = req.user.id;
        const currentUserRole = req.user.role;

        await client.query('BEGIN'); // BẮT ĐẦU TRANSACTION

        // 1. Lấy thông tin session và booking liên quan (khóa dòng FOR UPDATE để chống Race Condition)
        const sessionRes = await client.query(`
            SELECT s.*, b.parent_id, t.user_id AS tutor_user_id, s.session_rate
            FROM sessions s
            JOIN bookings b ON s.booking_id = b.id
            JOIN tutors t ON b.tutor_id = t.id
            WHERE s.id = $1
            FOR UPDATE
        `, [sessionId]);

        if (sessionRes.rows.length === 0) {
            await client.query('ROLLBACK');
            return res.status(404).json({ success: false, message: 'Không tìm thấy buổi học này' });
        }

        const session = sessionRes.rows[0];

        // 2. Kiểm tra nếu buổi học đã hoàn thành hoặc đã bị hủy
        if (session.status === 'completed') {
            await client.query('ROLLBACK');
            return res.status(400).json({ success: false, message: 'Buổi học này đã được cả hai bên xác nhận và thanh toán xong trước đó.' });
        }
        if (session.status === 'cancelled') {
            await client.query('ROLLBACK');
            return res.status(400).json({ success: false, message: 'Buổi học đã bị hủy, không thể xác nhận' });
        }

        let isTutor = (currentUserId === session.tutor_user_id || currentUserRole === 'admin');
        let isParent = (currentUserId === session.parent_id || currentUserRole === 'admin');

        if (!isTutor && !isParent) {
            await client.query('ROLLBACK');
            return res.status(403).json({ success: false, message: 'Bạn không phải là gia sư hoặc phụ huynh của buổi học này' });
        }

        let newTutorConfirmed = session.tutor_confirmed;
        let newParentConfirmed = session.parent_confirmed;

        // Cập nhật cờ xác nhận theo bên gửi yêu cầu
        if (isTutor) {
            newTutorConfirmed = true;
        }
        if (isParent) {
            newParentConfirmed = true;
        }

        let newStatus = session.status;
        let paymentExecuted = false;

        // 3. KIỂM TRA ĐIỀU KIỆN HAI CHIỀU:
        if (newTutorConfirmed && newParentConfirmed) {
            // == TRƯỜNG HỢP CẢ HAI BÊN ĐÃ XÁC NHẬN: CHUYỂN HOÀN THÀNH VÀ HẠCH TOÁN TIỀN ==
            newStatus = 'completed';

            // Cập nhật trạng thái session
            await client.query(`
                UPDATE sessions
                SET tutor_confirmed = TRUE,
                    parent_confirmed = TRUE,
                    tutor_confirmed_at = COALESCE(tutor_confirmed_at, CURRENT_TIMESTAMP),
                    parent_confirmed_at = COALESCE(parent_confirmed_at, CURRENT_TIMESTAMP),
                    status = 'completed'
                WHERE id = $1
            `, [sessionId]);

            // Thực hiện tính tiền: Cộng học phí vào số dư của Gia sư
            await client.query(`
                UPDATE users
                SET balance = balance + $1
                WHERE id = $2
            `, [session.session_rate, session.tutor_user_id]);

            // Ghi nhật ký giao dịch tài chính (Financial Ledger)
            const txCode = `TX-PAY-${sessionId}-${Date.now().toString().slice(-4)}`;
            await client.query(`
                INSERT INTO transactions (transaction_code, session_id, booking_id, payer_id, recipient_id, amount, transaction_type, status)
                VALUES ($1, $2, $3, $4, $5, $6, 'tuition_release', 'completed')
            `, [txCode, sessionId, session.booking_id, session.parent_id, session.tutor_user_id, session.session_rate]);

            paymentExecuted = true;
        } else if (newTutorConfirmed && !newParentConfirmed) {
            // Mới chỉ có Gia sư xác nhận
            newStatus = 'tutor_confirmed';
            await client.query(`
                UPDATE sessions
                SET tutor_confirmed = TRUE,
                    tutor_confirmed_at = CURRENT_TIMESTAMP,
                    status = 'tutor_confirmed'
                WHERE id = $1
            `, [sessionId]);
        } else if (!newTutorConfirmed && newParentConfirmed) {
            // Mới chỉ có Phụ huynh xác nhận
            newStatus = 'parent_confirmed';
            await client.query(`
                UPDATE sessions
                SET parent_confirmed = TRUE,
                    parent_confirmed_at = CURRENT_TIMESTAMP,
                    status = 'parent_confirmed'
                WHERE id = $1
            `, [sessionId]);
        }

        await client.query('COMMIT'); // HOÀN TẤT TRANSACTION THÀNH CÔNG

        res.json({
            success: true,
            message: paymentExecuted
                ? 'Tuyệt vời! Cả hai bên đã xác nhận hoàn thành buổi học. Tiền học phí đã được giải ngân thành công cho gia sư!'
                : 'Bạn đã xác nhận buổi học thành công. Đang chờ đối tác (Phụ huynh/Gia sư) xác nhận để giải ngân tiền học phí.',
            data: {
                session_id: sessionId,
                tutor_confirmed: newTutorConfirmed,
                parent_confirmed: newParentConfirmed,
                status: newStatus,
                payment_executed: paymentExecuted
            }
        });

    } catch (err) {
        await client.query('ROLLBACK'); // HOÀN NGUYÊN NẾU CÓ LỖI BẤT KỲ
        console.error('Session confirmation transaction error:', err);
        res.status(500).json({ success: false, message: 'Lỗi trong quá trình xử lý giao dịch', error: err.message });
    } finally {
        client.release();
    }
});

// =============================================================================
// 4. ADMIN ROUTES (Dành riêng cho App Admin Android)
// =============================================================================
app.get('/api/admin/stats', authenticateToken, requireRole('admin'), async (req, res) => {
    try {
        const usersCount = await pool.query('SELECT role, COUNT(*) FROM users GROUP BY role');
        const sessionsCount = await pool.query('SELECT status, COUNT(*) FROM sessions GROUP BY status');
        const revenue = await pool.query("SELECT COALESCE(SUM(amount), 0) AS total_revenue FROM transactions WHERE status = 'completed'");

        res.json({
            success: true,
            data: {
                users: usersCount.rows,
                sessions: sessionsCount.rows,
                total_revenue: parseFloat(revenue.rows[0].total_revenue)
            }
        });
    } catch (err) {
        res.status(500).json({ success: false, message: 'Lỗi thống kê Admin', error: err.message });
    }
});

// Root endpoint kiểm tra dịch vụ
app.get('/', (req, res) => {
    res.json({
        service: 'EduConnect Backend REST API',
        course: 'INT4211 · Lập trình trên các thiết bị di động',
        project: 'Đề tài 22: Nền tảng kết nối gia sư và phụ huynh',
        tech_highlight: 'Xác nhận hai chiều cho mỗi buổi dạy (Two-way Confirmation)',
        status: 'Online',
        version: '1.0.0'
    });
});

app.listen(PORT, () => {
    console.log(`EduConnect Backend REST API dang chay tai port ${PORT}`);
});
