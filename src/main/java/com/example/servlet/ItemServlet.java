package com.example.servlet;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.example.dao.ItemDAO;
import com.example.model.Item;

public class ItemServlet extends HttpServlet {
    private ItemDAO dao = ItemDAO.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)) {
            req.getRequestDispatcher("/form.jsp").forward(req, resp);
            return;
        } else if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Item item = dao.find(Integer.parseInt(id));
                req.setAttribute("item", item);
                req.getRequestDispatcher("/form.jsp").forward(req, resp);
                return;
            }
        } else if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                dao.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        List<Item> items = dao.findAll();
        req.setAttribute("items", items);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");

        Item item = new Item();
        if (id != null && !id.isEmpty()) {
            item.setId(Integer.parseInt(id));
        }
        item.setName(name);
        item.setDescription(description);
        dao.save(item);
        resp.sendRedirect(req.getContextPath() + "/items");
    }
}