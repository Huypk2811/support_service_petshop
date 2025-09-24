package com.example.supportservicecf.model;

import java.time.LocalDateTime;

/**
 * THÔNG BÁO ĐơN GIẢN CHO SHOP THÚ CƯNG
 * Chỉ cần: khuyến mãi, trạng thái đơn hàng
 */
public class NotificationSimple {
    private Long id;
    private String title;      // Tiêu đề (VD: "Khuyến mãi 50%")
    private String message;    // Nội dung chi tiết
    private String type;       // Loại: "promotion" hoặc "order_status"
    private LocalDateTime createdAt;

    public NotificationSimple() {
        this.createdAt = LocalDateTime.now();
    }

    public NotificationSimple(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}