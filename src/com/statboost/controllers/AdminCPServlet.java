package com.statboost.controllers;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/adminCP")
public class AdminCPServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (session != null && session.getAttribute("email") != null && User.isAdmin((String)session.getAttribute("email"))) {
            request.getRequestDispatcher("AdminCP.jsp").forward(request, response);
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "You are not authorized to view that page.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

}
