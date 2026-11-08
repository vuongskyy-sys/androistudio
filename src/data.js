// EduConnect - Comprehensive Production Mock Data Repository
// Full Vietnamese support with verified credentials, bios, schedules, and reviews

const INITIAL_TUTORS = [
  {
    id: "tut-01",
    name: "Nguyễn Minh Trang",
    avatar: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=400",
    title: "Thạc sĩ Sư Phạm Toán — ĐH Sư Phạm TP.HCM",
    university: "ĐH Sư Phạm TP.HCM",
    subjects: ["Toán 12", "Luyện thi ĐH", "Toán Olympic", "Toán 10-11"],
    category: "math",
    categoryLabel: "Toán học & STEM",
    hourlyRate: 250000,
    rating: 4.9,
    reviewsCount: 38,
    verified: true,
    featured: true,
    district: "Quận 1, TP.HCM",
    mode: "Tại nhà & Online",
    badge: "Thủ khoa Sư Phạm 2021",
    bio: "Thủ khoa đầu ra Sư phạm Toán 2021 với hơn 5 năm giảng dạy chuyên sâu khối 10-12. Phương pháp sư phạm hiện đại, tập trung xây dựng tư duy phản biện, giải quyết bài toán theo bản chất chứ không học vẹt công thức. Lộ trình cá nhân hóa 1-1 giúp hơn 120 học sinh đạt điểm 8.5+ môn Toán THPT Quốc gia.",
    experience: "5 năm luyện thi chuyên sâu",
    achievements: [
      "Giải Nhì Olympic Toán sinh viên toàn quốc 2020",
      "Chứng chỉ Nghiệp vụ Sư phạm Xuất sắc — ĐH Sư Phạm TP.HCM",
      "Biên soạn tài liệu 'Chinh phục Hình học Không gian 12'",
      "100% học sinh 2025 đỗ nguyện vọng 1 Đại học"
    ],
    schedule: {
      t2: ["evening"],
      t3: ["afternoon", "evening"],
      t4: ["morning", "evening"],
      t5: ["evening"],
      t6: ["afternoon"],
      t7: ["morning", "afternoon", "evening"],
      cn: ["morning", "afternoon"]
    }
  },
  {
    id: "tut-02",
    name: "Trần Đức Anh",
    avatar: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=400",
    title: "Kỹ sư Khoa Học Máy Tính — ĐH Bách Khoa TP.HCM",
    university: "ĐH Bách Khoa TP.HCM",
    subjects: ["Lập trình Python", "Tin học trẻ", "Toán Tư Duy", "C++ Nâng cao"],
    category: "tech",
    categoryLabel: "Tin học & Công nghệ",
    hourlyRate: 320000,
    rating: 5.0,
    reviewsCount: 45,
    verified: true,
    featured: true,
    district: "TP. Thủ Đức, TP.HCM",
    mode: "Online & Tại nhà",
    badge: "Kỹ sư AI Bách Khoa",
    bio: "Cựu học sinh Chuyên Tin Trường Lê Hồng Phong, tốt nghiệp loại Giỏi Khoa Học Máy Tính ĐH Bách Khoa TP.HCM. Có 4 năm kèm học sinh từ 9-18 tuổi làm quen với lập trình Python, thuật toán thi Tin học trẻ và phát triển tư duy logic giải quyết vấn đề.",
    experience: "4 năm giảng dạy Coding & AI",
    achievements: [
      "Huy chương Vàng Olympic Tin học Miền Trung & Tây Nguyên",
      "Tác giả giáo trình 'Python cho lứa tuổi THCS'",
      "Kèm 8 học sinh đoạt giải Tin học Trẻ TP.HCM (2023 - 2025)",
      "Kinh nghiệm làm việc tại Tập đoàn Công nghệ Top 3 Việt Nam"
    ],
    schedule: {
      t2: ["evening"],
      t3: ["evening"],
      t4: ["evening"],
      t5: ["evening"],
      t6: ["evening"],
      t7: ["morning", "afternoon", "evening"],
      cn: ["morning", "afternoon", "evening"]
    }
  },
  {
    id: "tut-03",
    name: "Lê Hoàng Phương Linh",
    avatar: "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&q=80&w=400",
    title: "Cử nhân Kinh Tế Đối Ngoại — ĐH Ngoại Thương TP.HCM",
    university: "ĐH Ngoại Thương (FTU2)",
    subjects: ["IELTS 7.5+", "Tiếng Anh Giao Tiếp", "Tiếng Anh Cấp 2", "Ngữ Pháp Chuyên"],
    category: "languages",
    categoryLabel: "Ngoại ngữ & IELTS",
    hourlyRate: 300000,
    rating: 4.9,
    reviewsCount: 29,
    verified: true,
    featured: false,
    district: "Quận Bình Thạnh, TP.HCM",
    mode: "Tại nhà & Online",
    badge: "IELTS 8.5 Overall",
    bio: "Sở hữu IELTS 8.5 (Listening 9.0, Reading 9.0, Speaking 8.5, Writing 8.0). Giảng dạy theo phương pháp Natural Immersion, chú trọng phản xạ giao tiếp tự nhiên và phát âm chuẩn IPA chuẩn Anh - Mỹ. Đã giúp hơn 80 học viên cán mốc IELTS 6.5 - 7.5 phục vụ du học.",
    experience: "4 năm luyện thi chứng chỉ quốc tế",
    achievements: [
      "IELTS 8.5 Overall (Cấp 2024 bởi IDP Vietnam)",
      "Chứng chỉ Giảng dạy Quốc tế TESOL 120 Giờ",
      "Cựu Đại biểu Hội nghị Thanh niên ASEAN tại Singapore",
      "Diễn giả Workshop 'Bí quyết bứt phá IELTS Writing Task 2'"
    ],
    schedule: {
      t2: ["morning", "afternoon"],
      t3: ["morning", "evening"],
      t4: ["afternoon", "evening"],
      t5: ["morning", "afternoon"],
      t6: ["afternoon", "evening"],
      t7: ["morning", "afternoon"],
      cn: ["evening"]
    }
  },
  {
    id: "tut-04",
    name: "Vũ Thanh Tùng",
    avatar: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=400",
    title: "Cử nhân Vật Lý Tiên Tiến — ĐH Khoa Học Tự Nhiên TP.HCM",
    university: "ĐH Khoa Học Tự Nhiên (ĐHQG)",
    subjects: ["Vật Lý 10-12", "Luyện thi ĐH Khối A", "Vật Lý THCS", "Thí nghiệm Vật lý"],
    category: "sciences",
    categoryLabel: "Khoa học Tự nhiên",
    hourlyRate: 220000,
    rating: 4.8,
    reviewsCount: 22,
    verified: true,
    featured: false,
    district: "Quận 5, TP.HCM",
    mode: "Tại nhà & Online",
    badge: "Top 1 Khoa Vật Lý",
    bio: "Chương trình Tiên tiến Vật lý chuẩn quốc tế. Phong cách dạy sinh động, biến các hiện tượng vật lý phức tạp (Sóng ánh sáng, Dao động điều hòa, Điện xoay chiều) thành các mô hình thực tế dễ hình dung và ghi nhớ lâu.",
    experience: "3.5 năm luyện thi Đại học khối A/A1",
    achievements: [
      "Giải Ba NCKH Sinh viên Cấp Thành phố môn Vật Lý",
      "Học bổng Khuyến khích Tài năng ĐHQG TP.HCM 3 năm liên tiếp",
      "Biên soạn sơ đồ tư duy (Mindmap) 24 chuyên đề Vật lý 12",
      "Giúp 100% học sinh mất gốc vượt ngưỡng 7.5 điểm thi Tốt nghiệp"
    ],
    schedule: {
      t2: ["evening"],
      t3: ["evening"],
      t4: ["afternoon", "evening"],
      t5: ["evening"],
      t6: ["evening"],
      t7: ["morning", "afternoon"],
      cn: ["morning", "afternoon", "evening"]
    }
  },
  {
    id: "tut-05",
    name: "Phạm Quỳnh Nga",
    avatar: "https://images.unsplash.com/photo-1594744803329-e58b31de8bf5?auto=format&fit=crop&q=80&w=400",
    title: "Bác Sĩ Đa Khoa Nội Trú — ĐH Y Dược TP.HCM",
    university: "ĐH Y Dược TP.HCM",
    subjects: ["Hóa Học 10-12", "Sinh Học 10-12", "Luyện thi Khối B", "Hóa Hữu cơ"],
    category: "sciences",
    categoryLabel: "Khoa học Tự nhiên",
    hourlyRate: 280000,
    rating: 5.0,
    reviewsCount: 31,
    verified: true,
    featured: true,
    district: "Quận 10, TP.HCM",
    mode: "Tại nhà & Online",
    badge: "Thủ khoa Khối B (29.25đ)",
    bio: "Thủ khoa đầu vào Khối B ĐH Y Dược TP.HCM năm 2019 với điểm 29.25 (Toán 9.5, Hóa 9.75, Sinh 10.0). Với 6 năm kinh nghiệm, cô Nga đồng hành cùng các bạn học sinh định hướng thi Y Dược, Ngoại Thương với kỹ năng giải nhanh trắc nghiệm và nắm vững nền tảng kiến thức cốt lõi.",
    experience: "6 năm đồng hành cùng học sinh thi Y Dược",
    achievements: [
      "Thủ khoa Khối B kỳ thi THPT Quốc gia 2019 (29.25 điểm)",
      "Bác sĩ Nội trú Chuyên ngành Hồi sức Cấp cứu",
      "Tác giả bộ 500 câu hỏi bẫy Hóa học vô cơ & hữu cơ",
      "Hơn 45 học sinh đã trúng tuyển ĐH Y Dược và ĐH Y Khoa Phạm Ngọc Thạch"
    ],
    schedule: {
      t2: ["evening"],
      t3: ["evening"],
      t4: ["evening"],
      t5: ["evening"],
      t6: ["evening"],
      t7: ["morning", "afternoon", "evening"],
      cn: ["morning", "afternoon"]
    }
  },
  {
    id: "tut-06",
    name: "Hoàng Nhật Minh",
    avatar: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&q=80&w=400",
    title: "Giảng viên Thỉnh giảng Sư Phạm — ĐH Sài Gòn",
    university: "ĐH Sài Gòn",
    subjects: ["Tiếng Anh Tiểu học", "Luyện thi vào 10 Chuyên", "Ngữ Pháp Căn Bản", "Phát âm Phonics"],
    category: "languages",
    categoryLabel: "Ngoại ngữ & Tiểu học",
    hourlyRate: 210000,
    rating: 4.8,
    reviewsCount: 19,
    verified: true,
    featured: false,
    district: "Quận Tân Bình, TP.HCM",
    mode: "Tại nhà & Online",
    badge: "Sư phạm Tiếng Anh Xuất sắc",
    bio: "Giảng viên thỉnh giảng giàu tâm huyết với phương pháp Gamification (học qua trò chơi tương tác), khơi gợi niềm yêu thích tiếng Anh tự nhiên cho học sinh tiểu học và cấp 2. Kiên nhẫn, gần gũi và thường xuyên cập nhật tiến độ chi tiết cho phụ huynh.",
    experience: "4 năm giảng dạy lứa tuổi 7-15",
    achievements: [
      "Chứng chỉ Cambridge CELTA Hạng A (Pass A)",
      "Giải Nhất Hội thi Nghiệp vụ Sư phạm Cấp Trường ĐH Sài Gòn",
      "Đào tạo 30+ học sinh thi đỗ vào lớp 10 chuyên Anh Nguyễn Thượng Hiền & Lê Hồng Phong"
    ],
    schedule: {
      t2: ["afternoon", "evening"],
      t3: ["afternoon"],
      t4: ["morning", "afternoon"],
      t5: ["afternoon", "evening"],
      t6: ["afternoon"],
      t7: ["morning", "afternoon"],
      cn: ["morning"]
    }
  },
  {
    id: "tut-07",
    name: "Đặng Bảo Châu",
    avatar: "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=400",
    title: "Kỹ sư Khoa Học Dữ Liệu & AI — ĐH Quốc Tế (ĐHQG TP.HCM)",
    university: "ĐH Quốc Tế (IU - ĐHQG)",
    subjects: ["Toán Cao Cấp", "Lập trình Python", "Xác Suất Thống Kê", "IELTS 7.0"],
    category: "tech",
    categoryLabel: "Toán Cao cấp & AI",
    hourlyRate: 350000,
    rating: 4.9,
    reviewsCount: 27,
    verified: true,
    featured: false,
    district: "Quận 7, TP.HCM",
    mode: "Online & Tại nhà",
    badge: "Chuyên gia Data Science",
    bio: "Tốt nghiệp Thủ khoa ngành Khoa Học Dữ Liệu trường ĐH Quốc Tế giảng dạy hoàn toàn bằng tiếng Anh. Chuyên kèm sinh viên đại học môn Toán cao cấp, Giải tích, Đại số tuyến tính, Xác suất thống kê và Machine Learning ứng dụng.",
    experience: "4 năm hướng dẫn sinh viên ĐH & Học sinh Quốc tế",
    achievements: [
      "Học bổng Toàn phần Hiệu trưởng ĐH Quốc Tế 4 năm",
      "Công bố bài báo khoa học tại Hội nghị IEEE Quốc tế 2024",
      "Cố vấn chuyên môn Cuộc thi Hackathon ĐHQG 2025"
    ],
    schedule: {
      t2: ["evening"],
      t3: ["morning", "evening"],
      t4: ["evening"],
      t5: ["morning", "evening"],
      t6: ["evening"],
      t7: ["afternoon", "evening"],
      cn: ["afternoon", "evening"]
    }
  }
];

const INITIAL_POSTS = [
  {
    id: "post-01",
    parentName: "Chị Mai Hương",
    phone: "0908 123 456",
    subject: "Toán 12 & Luyện thi ĐH",
    grade: "Lớp 12 (Khối A1)",
    category: "math",
    location: "Quận 1, TP.HCM",
    mode: "Tại nhà",
    budget: 300000,
    sessionsPerWeek: 3,
    preferredTime: "Tối Thứ 2, 4, 6 (19h00 - 21h00)",
    requirements: "Tìm gia sư Sư Phạm hoặc Bách Khoa có kinh nghiệm kèm học sinh mất gốc hình học không gian, mục tiêu thi tốt nghiệp đạt từ 8.2 điểm trở lên. Gia sư kiên nhẫn và có giáo án bài bản.",
    status: "Open",
    createdAt: "2026-10-23",
    applicantsCount: 2
  },
  {
    id: "post-02",
    parentName: "Anh Quốc Bảo",
    phone: "0918 789 012",
    subject: "Tiếng Anh Giao Tiếp & Cấp 2",
    grade: "Lớp 8",
    category: "languages",
    location: "Quận Bình Thạnh, TP.HCM",
    mode: "Tại nhà & Online",
    budget: 220000,
    sessionsPerWeek: 2,
    preferredTime: "Thứ 7 & Chủ Nhật (15h00 - 17h00)",
    requirements: "Cần gia sư phát âm chuẩn, phương pháp dạy vui vẻ tạo cảm hứng cho bé trai 13 tuổi còn nhút nhát, sợ nói tiếng Anh. Ưu tiên sinh viên Ngoại Thương hoặc Sư Phạm Anh.",
    status: "Open",
    createdAt: "2026-10-24",
    applicantsCount: 4
  },
  {
    id: "post-03",
    parentName: "Chị Lan Phương",
    phone: "0933 654 987",
    subject: "Lập trình Python & Tư duy logic",
    grade: "Lớp 6 (Học sinh 11 tuổi)",
    category: "tech",
    location: "TP. Thủ Đức, TP.HCM",
    mode: "Online qua Google Meet",
    budget: 350000,
    sessionsPerWeek: 2,
    preferredTime: "Tối Thứ 3, 5 (19h30 - 21h00)",
    requirements: "Tìm gia sư ngành CNTT kèm bé học tư duy lập trình căn bản, thiết kế mini-game đơn giản trên Python hoặc Scratch nâng cao. Gia sư có máy tính chia sẻ màn hình tốt.",
    status: "Open",
    createdAt: "2026-10-25",
    applicantsCount: 3
  },
  {
    id: "post-04",
    parentName: "Bác sĩ Minh Tuấn",
    phone: "0977 112 233",
    subject: "Hóa Học 11 — Nâng cao",
    grade: "Lớp 11 (Chuyên Hóa)",
    category: "sciences",
    location: "Quận 10, TP.HCM",
    mode: "Tại nhà",
    budget: 260000,
    sessionsPerWeek: 2,
    preferredTime: "Tối Thứ 4 & Thứ 6 (18h30 - 20h30)",
    requirements: "Cần gia sư chuyên kèm Hóa hữu cơ 11 và các dạng bài este/cacbohidrat nâng cao. Học sinh có nền tảng khá, muốn bứt phá đội tuyển học sinh giỏi cấp trường.",
    status: "Matched",
    createdAt: "2026-10-21",
    applicantsCount: 5
  },
  {
    id: "post-05",
    parentName: "Chị Thảo My",
    phone: "0909 888 999",
    subject: "IELTS Cấp tốc Target 6.5",
    grade: "Lớp 11 (Chuẩn bị du học)",
    category: "languages",
    location: "Quận 7, TP.HCM",
    mode: "Online & Tại nhà",
    budget: 320000,
    sessionsPerWeek: 3,
    preferredTime: "Thứ 2, 4, 6 (17h30 - 19h30)",
    requirements: "Lộ trình cấp tốc 4 tháng nâng từ band 5.0 lên 6.5+ để nộp hồ sơ học bổng du học Úc. Cần gia sư có chứng chỉ IELTS 7.5+ và kinh nghiệm sửa bài Writing kỹ lưỡng.",
    status: "Open",
    createdAt: "2026-10-26",
    applicantsCount: 1
  }
];

const INITIAL_REVIEWS = [
  {
    id: "rev-01",
    tutorId: "tut-01",
    parentName: "Phụ huynh em Gia Huy",
    studentGrade: "Lớp 12",
    rating: 5,
    date: "15/10/2026",
    comment: "Cô Trang dạy cực kỳ có tâm! Con tôi trước đây rất sợ môn Hình học không gian, nhưng chỉ sau 1 tháng được cô kèm đã tự tin làm được bài kiểm tra 1 tiết đạt 9.0 điểm. Cảm ơn cô rất nhiều!"
  },
  {
    id: "rev-02",
    tutorId: "tut-01",
    parentName: "Chị Bích Trâm (Q.1)",
    studentGrade: "Lớp 11",
    rating: 5,
    date: "02/10/2026",
    comment: "Giáo án mạch lạc, kiểm tra bài vở đầu giờ rất nghiêm túc. Cô Trang luôn gửi nhận xét tiến độ hàng tuần cho tôi theo dõi sát sao."
  },
  {
    id: "rev-03",
    tutorId: "tut-02",
    parentName: "Anh Hoàng Nam",
    studentGrade: "Lớp 7",
    rating: 5,
    date: "18/10/2026",
    comment: "Thầy Đức Anh truyền cảm hứng lập trình rất tốt! Bé nhà tôi trước đây ham chơi game thì nay đã tự code được một game xếp gạch bằng Python và khoe khắp nhà. Rất đáng đồng tiền."
  },
  {
    id: "rev-04",
    tutorId: "tut-02",
    parentName: "Chị Thu Thủy (Thủ Đức)",
    studentGrade: "Lớp 9",
    rating: 5,
    date: "20/09/2026",
    comment: "Kiến thức chuyên môn Bách Khoa sâu rộng, cách giải thích thuật toán cho học sinh cấp 2 rất dễ hiểu, không bị khô khan."
  },
  {
    id: "rev-05",
    tutorId: "tut-03",
    parentName: "Mẹ bé Bảo Ngọc",
    studentGrade: "Lớp 10",
    rating: 5,
    date: "12/10/2026",
    comment: "Cô Linh phát âm tiếng Anh cực kỳ hay, chuẩn bản xứ. Nhờ cô luyện phản xạ Speaking 1-1 mà bé nhà tôi đã dám đứng trước lớp thuyết trình tiếng Anh."
  },
  {
    id: "rev-06",
    tutorId: "tut-03",
    parentName: "Anh Duy Cường",
    studentGrade: "Lớp 11",
    rating: 4.8,
    date: "28/09/2026",
    comment: "Phương pháp dạy ngữ pháp thực tế, không bắt học thuộc vẹt. Chấm điểm bài viết IELTS Writing có highlight lỗi tỉ mỉ từng câu."
  },
  {
    id: "rev-07",
    tutorId: "tut-04",
    parentName: "Chị Thanh Hằng (Q.5)",
    studentGrade: "Lớp 12",
    rating: 5,
    date: "10/10/2026",
    comment: "Thầy Tùng có sơ đồ mindmap Vật lý rất dễ học, phần Điện xoay chiều vốn là nỗi ám ảnh của con tôi nay đã được giải quyết gọn gàng."
  },
  {
    id: "rev-08",
    tutorId: "tut-05",
    parentName: "Bác Sĩ Lê Văn Thành",
    studentGrade: "Lớp 12",
    rating: 5,
    date: "14/10/2026",
    comment: "Cô Nga là sinh viên Y Dược nên tư duy logic và kỹ năng sư phạm môn Hóa rất sắc sảo. Phương pháp làm bài trắc nghiệm nhanh giúp con tôi tiết kiệm rất nhiều thời gian."
  },
  {
    id: "rev-09",
    tutorId: "tut-06",
    parentName: "Chị Cẩm Tú (Bình Tân)",
    studentGrade: "Lớp 4",
    rating: 4.8,
    date: "05/10/2026",
    comment: "Thầy Minh rất vui tính, có nhiều trò chơi khởi động buổi học khiến bé nhà tôi luôn háo hức chờ đến giờ học gia sư."
  },
  {
    id: "rev-10",
    tutorId: "tut-07",
    parentName: "Anh Vũ Đình Trí",
    studentGrade: "Sinh viên năm 2 ĐH",
    rating: 5,
    date: "19/10/2026",
    comment: "Thầy Châu dạy môn Xác Suất Thống Kê đại học rất chắc tay, hướng dẫn chạy code phân tích trên Python trực quan, dễ qua môn điểm A."
  }
];

const INITIAL_BOOKINGS = [
  {
    id: "BK-2026-8801",
    tutorId: "tut-01",
    tutorName: "Nguyễn Minh Trang",
    tutorAvatar: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=400",
    parentName: "Chị Trần Thanh Hà",
    phone: "0903 445 667",
    studentName: "Bé Đức Trí",
    studentGrade: "Lớp 12 (Khối A1)",
    subject: "Toán 12",
    mode: "Học Trực tiếp tại nhà",
    date: "2026-10-30",
    timeSlot: "Tối (19:00 - 21:00)",
    note: "Mong muốn học thử 1 buổi kiểm tra kiến thức phần Đạo hàm và Khảo sát đồ thị hàm số.",
    status: "Confirmed",
    createdAt: "2026-10-24",
    meetLink: "https://meet.google.com/edu-math-7789"
  },
  {
    id: "BK-2026-8802",
    tutorId: "tut-02",
    tutorName: "Trần Đức Anh",
    tutorAvatar: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=400",
    parentName: "Anh Lê Quang",
    phone: "0915 223 344",
    studentName: "Bé Minh Khang",
    studentGrade: "Lớp 7",
    subject: "Lập trình Python",
    mode: "Học Online qua Google Meet",
    date: "2026-10-31",
    timeSlot: "Chiều (15:00 - 17:00)",
    note: "Bé chưa có kiến thức lập trình, gia sư hướng dẫn cài đặt môi trường và làm quen cấu trúc lệnh cơ bản.",
    status: "Pending",
    createdAt: "2026-10-25",
    meetLink: "https://meet.google.com/edu-py-3312"
  }
];

const INITIAL_USERS = [
  {
    id: "usr-parent",
    name: "Chị Mai Hương",
    role: "parent",
    phone: "0908 123 456",
    email: "maihuong.parent@educonnect.vn",
    district: "Quận 1, TP.HCM",
    studentName: "Bé Minh Quân (Lớp 10)",
    avatar: "https://images.unsplash.com/photo-1544005313-94ddf0286df2?auto=format&fit=crop&q=80&w=200",
    description: "Phụ huynh đang tìm gia sư Toán và Tiếng Anh định hướng du học cho con."
  },
  {
    id: "usr-tutor",
    name: "Nguyễn Minh Trang",
    role: "tutor",
    tutorId: "tut-01",
    phone: "0912 345 678",
    email: "minhtrang.supham@gmail.com",
    district: "Quận 1, TP.HCM",
    avatar: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=200",
    description: "Gia sư chuyên Toán 10-12, Thạc sĩ ĐH Sư Phạm TP.HCM."
  },
  {
    id: "usr-admin",
    name: "Quản trị viên EduConnect",
    role: "admin",
    phone: "1900 6868",
    email: "admin@educonnect.vn",
    district: "Trung tâm Điều hành TP.HCM",
    avatar: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&q=80&w=200",
    description: "Hệ thống kiểm duyệt gia sư & vận hành EduConnect Việt Nam."
  }
];

const INITIAL_MESSAGES = [
  {
    id: "conv-tut-01",
    tutorId: "tut-01",
    tutorName: "Cô Nguyễn Minh Trang",
    tutorAvatar: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=200",
    subject: "Toán 12 & Luyện thi ĐH",
    lastMessage: "Em đã nhận thông tin lịch học thử tối Thứ 6 rồi ạ. Em sẽ chuẩn bị bài kiểm tra năng lực đầu vào.",
    lastTime: "10:24",
    unread: 1,
    messages: [
      { id: "m1", sender: "parent", text: "Chào cô Trang, tôi muốn tìm hiểu lộ trình học thử cho bé Quân lớp 10 ạ.", time: "09:40" },
      { id: "m2", sender: "tutor", text: "Dạ chào chị Hương! Trong buổi đầu tiên em sẽ kiểm tra kiến thức nền tảng phần Hàm số và Lượng giác trong 45 phút, sau đó gửi chị đánh giá chi tiết kèm kế hoạch học tập ạ.", time: "10:15" },
      { id: "m3", sender: "tutor", text: "Em đã nhận thông tin lịch học thử tối Thứ 6 rồi ạ. Em sẽ chuẩn bị bài kiểm tra năng lực đầu vào.", time: "10:24" }
    ]
  },
  {
    id: "conv-tut-02",
    tutorId: "tut-02",
    tutorName: "Thầy Trần Đức Anh",
    tutorAvatar: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=200",
    subject: "Lập trình Python & Tin học",
    lastMessage: "Chị cài sẵn PyCharm hoặc VS Code trên máy bé giúp em nhé!",
    lastTime: "Hôm qua",
    unread: 0,
    messages: [
      { id: "m1", sender: "parent", text: "Thầy Đức Anh ơi, học online thì bé cần cài phần mềm gì trước không thầy?", time: "Hôm qua 14:10" },
      { id: "m2", sender: "tutor", text: "Dạ chị cài sẵn PyCharm hoặc VS Code trên máy bé giúp em nhé! Nếu cần em sẽ Ultraviewer hỗ trợ trước buổi học 15 phút ạ.", time: "Hôm qua 14:25" }
    ]
  }
];

const INITIAL_NOTIFICATIONS = [
  {
    id: "notif-1",
    title: "Buổi học thử đã được xác nhận!",
    desc: "Gia sư Nguyễn Minh Trang đã chấp nhận lịch học thử môn Toán 12 vào lúc 19:00 ngày 30/10.",
    time: "2 giờ trước",
    type: "booking",
    read: false
  },
  {
    id: "notif-2",
    title: "Gợi ý gia sư phù hợp mới",
    desc: "Thầy Vũ Thanh Tùng (Vật lý - ĐH KHTN) vừa cập nhật lịch rảnh vào tối Thứ 2 và Thứ 4 gần khu vực của bạn.",
    time: "5 giờ trước",
    type: "tutor",
    read: false
  },
  {
    id: "notif-3",
    title: "Báo cáo tiến độ học tập tuần 4",
    desc: "Bé Đức Trí đã hoàn thành 4 buổi học Toán và đạt 9/10 điểm bài test trắc nghiệm khảo sát hàm số.",
    time: "Hôm qua",
    type: "progress",
    read: true
  }
];

// Export for Node environment or attach to window
if (typeof module !== 'undefined' && module.exports) {
  module.exports = {
    INITIAL_TUTORS,
    INITIAL_POSTS,
    INITIAL_REVIEWS,
    INITIAL_BOOKINGS,
    INITIAL_USERS,
    INITIAL_MESSAGES,
    INITIAL_NOTIFICATIONS
  };
}

if (typeof window !== 'undefined') {
  window.INITIAL_TUTORS = INITIAL_TUTORS;
  window.INITIAL_POSTS = INITIAL_POSTS;
  window.INITIAL_REVIEWS = INITIAL_REVIEWS;
  window.INITIAL_BOOKINGS = INITIAL_BOOKINGS;
  window.INITIAL_USERS = INITIAL_USERS;
  window.INITIAL_MESSAGES = INITIAL_MESSAGES;
  window.INITIAL_NOTIFICATIONS = INITIAL_NOTIFICATIONS;
}
