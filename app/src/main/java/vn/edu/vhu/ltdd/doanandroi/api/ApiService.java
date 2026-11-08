package vn.edu.vhu.ltdd.doanandroi.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.vhu.ltdd.doanandroi.models.Tutor;

public interface ApiService {

    // Đăng nhập Admin
    @POST("api/auth/login")
    Call<Object> login(@Body Object loginRequest);

    // Lấy danh sách gia sư
    @GET("api/tutors")
    Call<Object> getTutors();

    // Thống kê tổng quan cho App Admin (biểu đồ doanh thu, số buổi)
    @GET("api/admin/stats")
    Call<Object> getAdminStats(@Header("Authorization") String token);

    // Xác nhận buổi học (Nghiệp vụ xác nhận 2 chiều)
    @POST("api/sessions/{id}/confirm")
    Call<Object> confirmSession(@Header("Authorization") String token, @Path("id") int sessionId);
}
