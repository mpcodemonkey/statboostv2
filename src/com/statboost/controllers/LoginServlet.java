package com.statboost.controllers;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true); //obtain or create the session object

        //authenticate the user
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User candidate = User.authenticate(email, password);
        if (candidate != null) {
            session.setAttribute("email", candidate.getEmail());
            if (candidate.getRole().equals("Admin")) {
                session.setAttribute("admin", "true");
            }
            response.sendRedirect("/");

        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "Invalid email or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }




    }

}
