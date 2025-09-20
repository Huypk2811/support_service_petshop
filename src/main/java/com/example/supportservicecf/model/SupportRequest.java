package com.example.supportservicecf.model;

import java.time.LocalDateTime;

public class SupportRequest {
    private Long id;
    private String name;
    private String email;
    private String message;
    private String status; 
    private LocalDateTime createdAt;

    // ===== Constructor =====
    public SupportRequest() {
        this.status = "Pending"; 
        this.createdAt = LocalDateTime.now();
    }

    public SupportRequest(String name, String email, String message, String status) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.status = (status != null && !status.isEmpty()) ? status : "Pending";
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getter & Setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
