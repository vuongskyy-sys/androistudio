import 'package:flutter/material.dart';

class SessionsScreen extends StatefulWidget {
  const SessionsScreen({super.key});

  @override
  State<SessionsScreen> createState() => _SessionsScreenState();
}

class _SessionsScreenState extends State<SessionsScreen> {
  // Dữ liệu mô phỏng đồng bộ với CSDL PostgreSQL
  final List<Map<String, dynamic>> _sessions = [
    {
      'id': 1,
      'code': 'SS-2026-101',
      'subject': 'Toán 12 & Luyện thi THPT',
      'tutor': 'Cô Nguyễn Minh Trang',
      'student': 'Bé Đức Trí',
      'date': '01/11/2026',
      'time': '19:00 - 21:00',
      'rate': '250.000 đ',
      'tutor_confirmed': true,
      'parent_confirmed': true,
      'status': 'completed',
    },
    {
      'id': 2,
      'code': 'SS-2026-102',
      'subject': 'Hình học không gian 12',
      'tutor': 'Cô Nguyễn Minh Trang',
      'student': 'Bé Đức Trí',
      'date': '05/11/2026',
      'time': '19:00 - 21:00',
      'rate': '250.000 đ',
      'tutor_confirmed': true,
      'parent_confirmed': false,
      'status': 'tutor_confirmed',
    },
    {
      'id': 3,
      'code': 'SS-2026-103',
      'subject': 'Ôn tập Đạo hàm & Khảo sát',
      'tutor': 'Cô Nguyễn Minh Trang',
      'student': 'Bé Đức Trí',
      'date': '12/11/2026',
      'time': '19:00 - 21:00',
      'rate': '250.000 đ',
      'tutor_confirmed': false,
      'parent_confirmed': false,
      'status': 'scheduled',
    },
  ];

  void _confirmSession(int sessionId, bool isParent) {
    final session = _sessions.firstWhere((s) => s['id'] == sessionId);
    final oldState = Map<String, dynamic>.from(session);

    setState(() {
      if (isParent) {
        session['parent_confirmed'] = true;
      } else {
        session['tutor_confirmed'] = true;
      }

      if (session['tutor_confirmed'] == true && session['parent_confirmed'] == true) {
        session['status'] = 'completed';
      } else if (session['tutor_confirmed'] == true) {
        session['status'] = 'tutor_confirmed';
      } else if (session['parent_confirmed'] == true) {
        session['status'] = 'parent_confirmed';
      }
    });

    final bool isCompleted = session['status'] == 'completed';

    // Hiển thị SnackBar có nút HOÀN TÁC (đúng yêu cầu bảng chấm slide của cô)
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          isCompleted
              ? 'Xác nhận hai chiều thành công! Buổi học đã hoàn tất và hạch toán học phí.'
              : 'Đã gửi xác nhận buổi học! Đang chờ đối tác xác nhận.',
        ),
        backgroundColor: isCompleted ? Colors.green.shade700 : Colors.blueGrey.shade800,
        action: SnackBarAction(
          label: 'HOÀN TÁC',
          textColor: Colors.yellow,
          onPressed: () {
            setState(() {
              session.addAll(oldState);
            });
          },
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Lịch Học & Xác Nhận 2 Chiều'),
      ),
      body: ListView.builder(
        padding: const EdgeInsets.all(16),
        itemCount: _sessions.length,
        itemBuilder: (context, index) {
          final s = _sessions[index];
          final bool isCompleted = s['status'] == 'completed';
          final bool isTutorConfirmed = s['tutor_confirmed'] == true;
          final bool isParentConfirmed = s['parent_confirmed'] == true;

          return Card(
            margin: const EdgeInsets.only(bottom: 16),
            color: Colors.white,
            elevation: 2,
            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(s['code'], style: const TextStyle(fontWeight: FontWeight.bold, color: Colors.blueGrey)),
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                        decoration: BoxDecoration(
                          color: isCompleted
                              ? Colors.green.shade100
                              : (isTutorConfirmed || isParentConfirmed)
                                  ? Colors.amber.shade100
                                  : Colors.blue.shade100,
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: Text(
                          isCompleted
                              ? '✓ Hoàn thành (Đã tính tiền)'
                              : isTutorConfirmed
                                  ? '⏳ Chờ Phụ huynh xác nhận'
                                  : isParentConfirmed
                                      ? '⏳ Chờ Gia sư xác nhận'
                                      : 'Đã lên lịch',
                          style: TextStyle(
                            fontSize: 12,
                            fontWeight: FontWeight.bold,
                            color: isCompleted
                                ? Colors.green.shade900
                                : (isTutorConfirmed || isParentConfirmed)
                                    ? Colors.amber.shade900
                                    : Colors.blue.shade900,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  Text(s['subject'], style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                  const SizedBox(height: 4),
                  Text('Gia sư: ${s['tutor']} · Học sinh: ${s['student']}'),
                  Text('Thời gian: ${s['date']} (${s['time']}) · Học phí: ${s['rate']}'),
                  const Divider(height: 24),
                  Row(
                    children: [
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              'Gia sư: ${isTutorConfirmed ? "✓ Đã xác nhận" : "Chưa xác nhận"}',
                              style: TextStyle(color: isTutorConfirmed ? Colors.green : Colors.grey, fontSize: 13),
                            ),
                            Text(
                              'Phụ huynh: ${isParentConfirmed ? "✓ Đã xác nhận" : "Chưa xác nhận"}',
                              style: TextStyle(color: isParentConfirmed ? Colors.green : Colors.grey, fontSize: 13),
                            ),
                          ],
                        ),
                      ),
                      if (!isCompleted)
                        ElevatedButton.icon(
                          onPressed: () => _confirmSession(s['id'], true),
                          icon: const Icon(Icons.check_circle_outline, size: 18),
                          label: const Text('Phụ huynh xác nhận'),
                          style: ElevatedButton.styleFrom(
                            backgroundColor: const Color(0xFF10B981),
                            foregroundColor: Colors.white,
                          ),
                        ),
                    ],
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}
