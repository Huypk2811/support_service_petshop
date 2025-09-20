package com.example.supportservicecf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.supportservicecf.model.SupportRequest;
import com.example.supportservicecf.util.DBConnection;

public class SupportRequestDAO {

    // Thêm request và trả về object sau khi lưu (có id)
    public SupportRequest save(SupportRequest r) {
        String sql = "INSERT INTO support_requests (name, email, message, status, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, r.getName());
            stmt.setString(2, r.getEmail());
            stmt.setString(3, r.getMessage());
            stmt.setString(4, r.getStatus());
            stmt.setTimestamp(5, Timestamp.valueOf(r.getCreatedAt()));

            stmt.executeUpdate();

            // Lấy id tự tăng và gán lại cho object
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getLong(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    // Lấy tất cả
    public List<SupportRequest> findAll() {
        List<SupportRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM support_requests ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SupportRequest r = new SupportRequest();
                r.setId(rs.getLong("id"));
                r.setName(rs.getString("name"));
                r.setEmail(rs.getString("email"));
                r.setMessage(rs.getString("message"));
                r.setStatus(rs.getString("status"));
                r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy theo id
    public SupportRequest findById(Long id) {
        String sql = "SELECT * FROM support_requests WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SupportRequest r = new SupportRequest();
                    r.setId(rs.getLong("id"));
                    r.setName(rs.getString("name"));
                    r.setEmail(rs.getString("email"));
                    r.setMessage(rs.getString("message"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return r;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public SupportRequest update(SupportRequest r) {
        String sql = "UPDATE support_requests SET name=?, email=?, message=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, r.getName());
            stmt.setString(2, r.getEmail());
            stmt.setString(3, r.getMessage());
            stmt.setString(4, r.getStatus());
            stmt.setLong(5, r.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    // Delete
    public boolean delete(Long id) {
        String sql = "DELETE FROM support_requests WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
