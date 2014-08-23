package com.statboost.controllers;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sam Kerr
 * 3:33 PM on 8/6/2014
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (User.find(request.getParameter("email")) == null) { //check that the user email doesn't already exist
            User.insert(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), request.getParameter("password"), "Customer");
            response.sendRedirect("login");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "The email you have chosen is currently taken, please try another.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }

}
