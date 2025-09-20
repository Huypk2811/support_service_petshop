package com.example.supportservicecf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.supportservicecf.model.Notification;
import com.example.supportservicecf.util.DBConnection;

public class NotificationDAO {

    // Thêm notification và trả về object sau khi lưu (có id)
    public Notification save(Notification n) {
        String sql = "INSERT INTO notifications (title, message, type, createdAt) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, n.getTitle());
            stmt.setString(2, n.getMessage());
            stmt.setString(3, n.getType());
            stmt.setTimestamp(4, Timestamp.valueOf(n.getCreatedAt()));

            stmt.executeUpdate();

            // Lấy id được sinh tự động (AUTO_INCREMENT)
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    n.setId(rs.getLong(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    // Lấy tất cả notification
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY createdAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Notification n = new Notification();
                n.setId(rs.getLong("id"));
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                n.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy notification theo type
    public List<Notification> findByType(String type) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE type = ? ORDER BY createdAt DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setId(rs.getLong("id"));
                    n.setTitle(rs.getString("title"));
                    n.setMessage(rs.getString("message"));
                    n.setType(rs.getString("type"));
                    n.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    list.add(n);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
