package com.example.supportservicecf.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.supportservicecf.dao.NotificationDAO;
import com.example.supportservicecf.model.Notification;
import com.google.gson.Gson;

/**
 * SERVLET ĐƠN GIẢN CHO THÔNG BÁO
 * Chỉ có: Xem tất cả & Xem theo loại
 */
@WebServlet("/api/notifications/*")
public class NotificationServletSimple extends HttpServlet {
    private NotificationDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new NotificationDAO();
        gson = new Gson();
    }

    // XEM THÔNG BÁO
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Lấy tất cả thông báo
            List<Notification> list = dao.findAll();
            resp.getWriter().write(gson.toJson(list));
            
        } else if (pathInfo.equals("/promotions")) {
            // Chỉ lấy khuyến mãi
            List<Notification> list = dao.findByType("promotion");
            resp.getWriter().write(gson.toJson(list));
            
        } else if (pathInfo.equals("/orders")) {
            // Chỉ lấy trạng thái đơn hàng
            List<Notification> list = dao.findByType("order_status");
            resp.getWriter().write(gson.toJson(list));
            
        } else {
            resp.setStatus(404);
            resp.getWriter().write("{\"error\":\"Không tìm thấy\"}");
        }
    }
}