package com.example.supportservicecf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.supportservicecf.dao.SupportRequestDAO;
import com.example.supportservicecf.model.SupportRequest;
import com.google.gson.Gson;

/**
 * SERVLET ĐƠN GIẢN CHO YÊU CẦU HỖ TRỢ
 * Chỉ có: Xem danh sách & Gửi câu hỏi mới
 */
@WebServlet("/api/support/*")
public class SupportServletSimple extends HttpServlet {
    private SupportRequestDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new SupportRequestDAO();
        gson = new Gson();
    }

    // XEM DANH SÁCH CÂU HỎI
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<SupportRequest> list = dao.findAll();
        
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gson.toJson(list));
    }

    // GỬI CÂU HỎI MỚI
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        SupportRequest request = gson.fromJson(reader, SupportRequest.class);

        // Validation đơn giản
        if (request.getName() == null || request.getName().trim().isEmpty() ||
            request.getEmail() == null || request.getEmail().trim().isEmpty() ||
            request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Vui lòng điền đầy đủ thông tin\"}");
            return;
        }

        SupportRequest saved = dao.save(request);
        
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(gson.toJson(saved));
    }
}