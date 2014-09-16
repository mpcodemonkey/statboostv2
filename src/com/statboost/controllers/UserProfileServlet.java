package com.statboost.controllers;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (User.isLoggedIn(session)) {

            User user = User.find((String)session.getAttribute("email"));
            request.setAttribute("user", user);



            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (User.isLoggedIn(session)) {
            User user = User.find((String) session.getAttribute("email"));
            user.setUsrAddress1(request.getParameter("address1"));
            user.setUsrAddress2(request.getParameter("address2"));
            user.setUsrCity(request.getParameter("city"));
            user.setUsrState(request.getParameter("state"));
            user.setUsrZip(request.getParameter("zip"));
            user.setUsrPhone(request.getParameter("phone"));
            if (request.getParameter("newsletter") != null) {
                user.setUsrNewsletter(Byte.MAX_VALUE);
            } else {
                user.setUsrNewsletter(Byte.MIN_VALUE);
            }
            user.setUsrDciNumber(request.getParameter("dcinumber"));
            User.update(user);
            doGet(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

}
