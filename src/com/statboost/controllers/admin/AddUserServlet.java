package com.statboost.controllers.admin;

import com.statboost.models.actor.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/admin/addNewUser")
public class AddUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //validate the administrator
        if (User.isAdmin(session)) {
            if(request.getParameter("type") != null) {

                if (request.getParameter("type").equals("admin")) {
                    request.setAttribute("type", "Administrator");

                }
                if (request.getParameter("type").equals("employee")) {
                    request.setAttribute("type", "Employee");

                }
                if (request.getParameter("type").equals("customer")) {
                    request.setAttribute("type", "Customer");

                }

                request.getRequestDispatcher("AddNewUser.jsp").forward(request, response);
            } else {
                response.sendRedirect("/admin/adminCP");
            }

        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        //validate the administrator
        if (User.isAdmin(session)) {
            if (User.find(request.getParameter("email")) == null) { //check that the user email doesn't already exist
                //Handle Admin insertion
                if (request.getParameter("type")!=null && request.getParameter("type").equals("admin")) {
                    User.insert(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), request.getParameter("password"), "Admin");
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Administrator account has been successfully created for: " + request.getParameter("firstname") + " " + request.getParameter("lastname"));
                }
                //Handle Employee insertion
                 else if (request.getParameter("type")!=null && request.getParameter("type").equals("employee")) {
                    User.insert(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), request.getParameter("password"), "Employee");
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Employee account has been successfully created for: " + request.getParameter("firstname") + " " + request.getParameter("lastname"));
                }
                //Handle Customer insertion
                 else if (request.getParameter("type")!=null && request.getParameter("type").equals("customer")) {
                    User.insert(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), request.getParameter("password"), "Customer");
                    request.setAttribute("alertType", "warning");
                    request.setAttribute("alert", "Customer account has been successfully created for: " + request.getParameter("firstname") + " " + request.getParameter("lastname"));
                } else {
                    request.setAttribute("alertType", "danger");
                    request.setAttribute("alert", "Something went wrong.");
                }

                request.getRequestDispatcher("AdminCP.jsp").forward(request, response);

            } else {
                request.setAttribute("alertType", "danger");
                request.setAttribute("alert", "The email you have chosen is currently taken, please try another.");
                request.getRequestDispatcher("AddNewUser.jsp").forward(request, response);
            }
        }
    }




}
