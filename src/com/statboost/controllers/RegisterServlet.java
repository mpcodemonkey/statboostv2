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

        //validate matching passwords
        if(!request.getParameter("password").equals(request.getParameter("passwordConf"))) {
            request.setAttribute("alertType", "warning");
            request.setAttribute("alert", "Your passwords do not match.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else if (User.find(request.getParameter("email")) == null) { //check that the user email doesn't already exist
            String usrFirstName = request.getParameter("firstname");
            String usrLastName = request.getParameter("lastname");
            String usrEmail = request.getParameter("email");
            String usrPassword = request.getParameter("password");
            String usrAddress1 = request.getParameter("address1");
            String usrAddress2 = request.getParameter("address2");
            String usrCity = request.getParameter("city");
            String usrState = request.getParameter("state");
            String usrZip = request.getParameter("zip");
            String usrPhone = request.getParameter("phone");
            //usrPhone = usrPhone.replace("(", "").replace(")", "").replaceAll("-", "");
            Byte usrNewsletter = new Byte(Byte.MIN_VALUE);
            if (request.getParameter("newsletter")!=null && request.getParameter("newsletter").equals("true")) {
                usrNewsletter = new Byte(Byte.MAX_VALUE); //user wants newsletter
            }
            String usrDciNumber = request.getParameter("dcinumber");
            User.insert(usrFirstName, usrLastName, usrEmail, usrPassword, "Customer", usrAddress1, usrAddress2, usrCity, usrState, usrZip, usrPhone, usrNewsletter, usrDciNumber);
            response.sendRedirect("login");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alert", "The email you have chosen is currently taken, please try another.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }

}
