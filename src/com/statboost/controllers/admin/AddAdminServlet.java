package com.statboost.controllers.admin;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/addNewAdmin")
public class AddAdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //check that the user is logged in and check that the user is an admin either through session attribute or db check
        if (session != null && session.getAttribute("email") != null &&
            (session.getAttribute("admin").equals("true") || User.isAdmin((String)session.getAttribute("email")))) {

                request.getRequestDispatcher("admin/AddNewAdmin.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (User.find(request.getParameter("email")) == null) { //check that the user email doesn't already exist
            User.insert(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), request.getParameter("password"), "Admin");
            request.setAttribute("alertType", "success");
            request.setAttribute("alert", "Administrator account has been successfully created for: " + request.getParameter("firstname") + " " + request.getParameter("lastname"));
            request.getRequestDispatcher("admin/AdminCP.jsp").forward(request, response);
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "The email you have chosen is currently taken, please try another.");
            request.getRequestDispatcher("admin/AddNewAdmin.jsp").forward(request, response);
        }

    }


}
