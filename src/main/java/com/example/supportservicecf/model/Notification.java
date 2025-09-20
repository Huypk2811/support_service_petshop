package com.example.supportservicecf.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private String title;      
    private String message;    
    private String type;       
    private LocalDateTime createdAt;

    // ===== Constructor =====
    public Notification() {
        this.createdAt = LocalDateTime.now();
    }

    public Notification(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getter & Setter =====
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
