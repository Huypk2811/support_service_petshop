package com.example.supportservicecf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.supportservicecf.dao.SupportRequestDAO;
import com.example.supportservicecf.model.SupportRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * SERVLET XỬ LÝ API CHO SUPPORT REQUEST
 * URL: /api/support/*
 * (Được cấu hình trong web.xml, KHÔNG dùng @WebServlet)
 */
public class SupportServlet extends HttpServlet {

    private SupportRequestDAO supportRequestDAO;
    private Gson gson;

    @Override
    public void init() {
        supportRequestDAO = new SupportRequestDAO();

        // Gson custom để serialize/deserialize LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                src == null ? null : context.serialize(src.format(formatter)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                json == null ? null : LocalDateTime.parse(json.getAsString(), formatter))
                .create();
    }

    /** GET: LẤY TẤT CẢ HOẶC LẤY THEO ID */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Trả về toàn bộ danh sách (ORDER BY created_at DESC trong DAO)
            List<SupportRequest> list = supportRequestDAO.findAll();
            resp.getWriter().write(gson.toJson(list));
        } else {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                SupportRequest sr = supportRequestDAO.findById(id);
                if (sr != null) {
                    resp.getWriter().write(gson.toJson(sr));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Not found\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid ID format\"}");
            }
        }
    }

    /** POST: THÊM MỚI YÊU CẦU */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        BufferedReader reader = req.getReader();
        SupportRequest request = gson.fromJson(reader, SupportRequest.class);

        // Nếu client không gửi createdAt, tự set
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(LocalDateTime.now());
        }

        supportRequestDAO.save(request);
        resp.getWriter().write(gson.toJson(request));
    }

    /** PUT: CẬP NHẬT THEO ID */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                BufferedReader reader = req.getReader();
                SupportRequest updateReq = gson.fromJson(reader, SupportRequest.class);
                updateReq.setId(id);

                supportRequestDAO.update(updateReq);
                resp.getWriter().write(gson.toJson(updateReq));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid ID format\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing ID in path\"}");
        }
    }

    /** DELETE: XÓA THEO ID */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                boolean deleted = supportRequestDAO.delete(id);
                if (deleted) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Not found\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid ID format\"}");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing ID in path\"}");
        }
    }
}
