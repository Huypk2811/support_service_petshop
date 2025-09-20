package com.example.supportservicecf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.example.supportservicecf.dao.NotificationDAO;
import com.example.supportservicecf.model.Notification;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/notifications/*")
public class NotificationServlet extends HttpServlet {
    private NotificationDAO notificationDAO;
    private Gson gson;

    @Override
    public void init() {
        notificationDAO = new NotificationDAO();
        gson = new Gson();
    }

    // Lấy tất cả hoặc theo type
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Notification> list = notificationDAO.findAll();
            resp.getWriter().write(gson.toJson(list));
        } else if (pathInfo.startsWith("/type/")) {
            String type = pathInfo.substring("/type/".length());
            List<Notification> list = notificationDAO.findByType(type);
            resp.getWriter().write(gson.toJson(list));
        }
    }

    // Thêm notification mới
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Notification notification = gson.fromJson(reader, Notification.class);

        Notification saved = notificationDAO.save(notification); // save trả về object
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(saved));
    }
}
