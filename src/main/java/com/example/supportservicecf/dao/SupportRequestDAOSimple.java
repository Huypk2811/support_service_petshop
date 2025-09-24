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

/**
 * DAO ĐƠN GIẢN - CHỈ CẦN THIẾT CHO SHOP THÚ CƯNG
 * Chỉ có: Thêm mới & Xem danh sách
 */
public class SupportRequestDAOSimple {

    // THÊM CÂU HỎI MỚI
    public SupportRequest save(SupportRequest request) {
        String sql = "INSERT INTO support_requests(name, email, message, status, created_at) VALUES(?,?,?,?,?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, request.getName());
            ps.setString(2, request.getEmail());
            ps.setString(3, request.getMessage());
            ps.setString(4, "Pending");
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            ps.executeUpdate();

            // Lấy ID vừa tạo
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    request.setId(keys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    // XEM TẤT CẢ CÂU HỎI (MỚI NHẤT TRƯỚC)
    public List<SupportRequest> findAll() {
        List<SupportRequest> list = new ArrayList<>();
        String sql = "SELECT id, name, email, message, status, created_at FROM support_requests ORDER BY created_at DESC";
        
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
                if (ts != null) {
                    s.setCreatedAt(ts.toLocalDateTime());
                }
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}