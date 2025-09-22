package com.example.supportservicecf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.supportservicecf.model.SupportRequest;
import com.example.supportservicecf.util.DBConnection;

public class SupportRequestDAO {

    // THÊM MỚI
    public SupportRequest save(SupportRequest r) {
        String sql = "INSERT INTO support_requests(name,email,message,status,created_at) VALUES(?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getEmail());
            ps.setString(3, r.getMessage());
            ps.setString(4, r.getStatus() == null ? "Pending" : r.getStatus());
            LocalDateTime now = r.getCreatedAt() != null ? r.getCreatedAt() : LocalDateTime.now();
            ps.setTimestamp(5, Timestamp.valueOf(now));

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getLong(1));
            }
            r.setCreatedAt(now);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    // LẤY TẤT CẢ
    public List<SupportRequest> findAll() {
        List<SupportRequest> list = new ArrayList<>();
        String sql = "SELECT id,name,email,message,status,created_at FROM support_requests ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SupportRequest s = new SupportRequest();
                s.setId(rs.getLong("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setMessage(rs.getString("message"));
                s.setStatus(rs.getString("status"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) s.setCreatedAt(ts.toLocalDateTime());
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // LẤY THEO ID
    public SupportRequest findById(long id) {
        String sql = "SELECT id,name,email,message,status,created_at FROM support_requests WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SupportRequest s = new SupportRequest();
                    s.setId(rs.getLong("id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setMessage(rs.getString("message"));
                    s.setStatus(rs.getString("status"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) s.setCreatedAt(ts.toLocalDateTime());
                    return s;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CẬP NHẬT
    public SupportRequest update(SupportRequest r) {
        String sql = "UPDATE support_requests SET name=?,email=?,message=?,status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getEmail());
            ps.setString(3, r.getMessage());
            ps.setString(4, r.getStatus());
            ps.setLong(5, r.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    // XÓA
    public boolean delete(long id) {
        String sql = "DELETE FROM support_requests WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
