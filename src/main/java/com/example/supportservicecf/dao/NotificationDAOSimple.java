package com.example.supportservicecf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.supportservicecf.model.Notification;
import com.example.supportservicecf.util.DBConnection;

/**
 * DAO ĐƠN GIẢN CHO THÔNG BÁO
 * Chỉ cần: Xem tất cả & Xem theo loại
 */
public class NotificationDAOSimple {

    // XEM TẤT CẢ THÔNG BÁO (MỚI NHẤT TRƯỚC)
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY createdAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Notification n = new Notification();
                n.setId(rs.getLong("id"));
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                
                Timestamp ts = rs.getTimestamp("createdAt");
                if (ts != null) {
                    n.setCreatedAt(ts.toLocalDateTime());
                }
                list.add(n);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách thông báo: " + e.getMessage());
        }
        return list;
    }

    // XEM THEO LOẠI (promotion hoặc order_status)
    public List<Notification> findByType(String type) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE type = ? ORDER BY createdAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, type);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setId(rs.getLong("id"));
                    n.setTitle(rs.getString("title"));
                    n.setMessage(rs.getString("message"));
                    n.setType(rs.getString("type"));
                    
                    Timestamp ts = rs.getTimestamp("createdAt");
                    if (ts != null) {
                        n.setCreatedAt(ts.toLocalDateTime());
                    }
                    list.add(n);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy thông báo theo loại: " + e.getMessage());
        }
        return list;
    }
}