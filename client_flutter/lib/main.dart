import 'package:flutter/material.dart';
import 'screens/sessions_screen.dart';

void main() {
  runApp(const EduConnectClientApp());
}

class EduConnectClientApp extends StatelessWidget {
  const EduConnectClientApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'EduConnect Client',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF0F172A),
          primary: const Color(0xFF0F172A),
          secondary: const Color(0xFF10B981),
          surface: const Color(0xFFF1F5F9),
        ),
        scaffoldBackgroundColor: const Color(0xFFF1F5F9),
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFF0F172A),
          foregroundColor: Colors.white,
          elevation: 0,
        ),
      ),
      home: const MainNavigationScreen(),
    );
  }
}

class MainNavigationScreen extends StatefulWidget {
  const MainNavigationScreen({super.key});

  @override
  State<MainNavigationScreen> createState() => _MainNavigationScreenState();
}

class _MainNavigationScreenState extends State<MainNavigationScreen> {
  int _currentIndex = 0;

  final List<Widget> _screens = const [
    TutorListTab(),
    SessionsScreen(),
    ProfileTab(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_currentIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _currentIndex,
        onDestinationSelected: (index) => setState(() => _currentIndex = index),
        destinations: const [
          NavigationDestination(icon: Icon(Icons.school_outlined), selectedIcon: Icon(Icons.school), label: 'Gia sư'),
          NavigationDestination(icon: Icon(Icons.calendar_month_outlined), selectedIcon: Icon(Icons.calendar_month), label: 'Buổi học'),
          NavigationDestination(icon: Icon(Icons.person_outline), selectedIcon: Icon(Icons.person), label: 'Cá nhân'),
        ],
      ),
    );
  }
}

class TutorListTab extends StatelessWidget {
  const TutorListTab({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('EduConnect · Tìm Gia Sư'),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Card(
            color: Colors.white,
            elevation: 2,
            child: ListTile(
              leading: const CircleAvatar(
                backgroundImage: NetworkImage('https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=200'),
              ),
              title: const Text('Cô Nguyễn Minh Trang', style: TextStyle(fontWeight: FontWeight.bold)),
              subtitle: const Text('Thạc sĩ Sư Phạm Toán · 250.000₫/h\n⭐ 4.9 (38 đánh giá)'),
              isThreeLine: true,
              trailing: ElevatedButton(
                onPressed: () {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Đang mở form đặt lịch học...')),
                  );
                },
                child: const Text('Đặt lịch'),
              ),
            ),
          ),
          const SizedBox(height: 12),
          Card(
            color: Colors.white,
            elevation: 2,
            child: ListTile(
              leading: const CircleAvatar(
                backgroundImage: NetworkImage('https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=200'),
              ),
              title: const Text('Thầy Trần Đức Anh', style: TextStyle(fontWeight: FontWeight.bold)),
              subtitle: const Text('Kỹ sư AI ĐH Bách Khoa · 320.000₫/h\n⭐ 5.0 (45 đánh giá)'),
              isThreeLine: true,
              trailing: ElevatedButton(
                onPressed: () {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Đang mở form đặt lịch học...')),
                  );
                },
                child: const Text('Đặt lịch'),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class ProfileTab extends StatelessWidget {
  const ProfileTab({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Hồ Sơ Cá Nhân')),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            const CircleAvatar(
              radius: 40,
              backgroundImage: NetworkImage('https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=200'),
            ),
            const SizedBox(height: 12),
            const Text('Chị Mai Hương (Phụ huynh)', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const Text('parent@educonnect.vn · 0908 123 456', style: TextStyle(color: Colors.grey)),
            const SizedBox(height: 24),
            ListTile(
              leading: const Icon(Icons.account_balance_wallet_outlined),
              title: const Text('Số dư ví học phí'),
              trailing: const Text('1.200.000 đ', style: TextStyle(fontWeight: FontWeight.bold, color: Colors.green)),
            ),
            const Divider(),
            ListTile(
              leading: const Icon(Icons.verified_outlined),
              title: const Text('Đề tài 22 - INT4211'),
              subtitle: const Text('Nền tảng kết nối gia sư và phụ huynh'),
            ),
          ],
        ),
      ),
    );
  }
}
