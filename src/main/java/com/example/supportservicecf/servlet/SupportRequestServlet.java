package com.example.supportservicecf.servlet;

import com.example.supportservicecf.dao.SupportRequestDAO;
import com.example.supportservicecf.model.SupportRequest;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/support-requests/*")
public class SupportRequestServlet extends HttpServlet {
    private SupportRequestDAO supportRequestDAO;
    private Gson gson;

    @Override
    public void init() {
        supportRequestDAO = new SupportRequestDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<SupportRequest> list = supportRequestDAO.findAll();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        SupportRequest request = gson.fromJson(reader, SupportRequest.class);

        SupportRequest saved = supportRequestDAO.save(request);
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(saved));
    }
}
