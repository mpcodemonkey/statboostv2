package com.statboost.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (session != null) {
            session.removeAttribute("email");
            session.invalidate();
            request.setAttribute("alertType", "info");
            request.setAttribute("alert", "You have been logged out successfully.");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (session != null) {
            session.removeAttribute("email");
            session.invalidate();
            request.setAttribute("alertType", "info");
            request.setAttribute("alert", "You have been logged out successfully.");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}