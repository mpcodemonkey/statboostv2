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
        //authenticate the user's credentials
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User candidate = User.authenticate(email, password);
        if (candidate == null) { //User doesn't exist or password invalid
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "Invalid email or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else if (candidate != null && candidate.getUsrActive().equals(Byte.MIN_VALUE)) { //user account is deactivated
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "Your account has been deactivated. Please contact an administrator to reactivate your account.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else { //create the session and 'log in' the user. Set flag if admin or employee
            HttpSession session = request.getSession(true); //obtain or create the session object
            session.setAttribute("email", candidate.getUsrEmail());
            if (candidate.getUsrRole().equals("Admin")) {
                session.setAttribute("admin", "true");
            }
            if (candidate.getUsrRole().equals("Employee")) {
                session.setAttribute("employee", "true");
            }
            response.sendRedirect("/");
        }

    }

}
