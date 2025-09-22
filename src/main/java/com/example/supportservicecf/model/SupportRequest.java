package com.example.supportservicecf.model;

import java.time.LocalDateTime;

/**
 * MODEL LƯU THÔNG TIN YÊU CẦU HỖ TRỢ
 * Mapping trực tiếp với bảng `support_requests` trong CSDL
 */
public class SupportRequest {

    private Long id;               // KHÓA CHÍNH
    private String name;           // TÊN NGƯỜI GỬI
    private String email;          // EMAIL LIÊN HỆ
    private String message;        // NỘI DUNG HỖ TRỢ
    private String status;         // TRẠNG THÁI (Pending/Done/...)
    private LocalDateTime createdAt; // THỜI GIAN TẠO

    // ===== CONSTRUCTOR MẶC ĐỊNH =====
    public SupportRequest() {
        this.status = "Pending";           // MẶC ĐỊNH CHƯA XỬ LÝ
        this.createdAt = LocalDateTime.now(); // THỜI GIAN HIỆN TẠI
    }

    // ===== CONSTRUCTOR ĐẦY ĐỦ =====
    public SupportRequest(String name, String email, String message, String status) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.status = (status != null && !status.trim().isEmpty()) ? status : "Pending";
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTER & SETTER =====
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = (status != null && !status.trim().isEmpty()) ? status : "Pending";
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ===== TO STRING (HỖ TRỢ DEBUG) =====
    @Override
    public String toString() {
        return "SupportRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
