package com.statboost.controllers;

import com.statboost.models.actor.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (User.isLoggedIn(session)) {

            User user = User.find((String)session.getAttribute("email"));
            request.setAttribute("user", user);

            request.getRequestDispatcher("/UserProfile.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        if (User.isLoggedIn(session)) {

            //handle password change
            if (request.getParameter("oldPassword") != null && request.getParameter("newPassword") != null) {
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");

                //validate old password with user before change
                User candidate = User.authenticate((String)session.getAttribute("email"), oldPassword);
                if (candidate != null) {
                    String newHashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    candidate.setUsrPassword(newHashed);
                    User.update(candidate); //update user with new password
                    request.setAttribute("alert", "Your password has been changed successfully.");
                    request.setAttribute("alertType", "success");
                } else {
                    request.setAttribute("alert", "Your old password was incorrect, please try that again.");
                    request.setAttribute("alertType", "danger");
                }
            }

            //handle profile update
            if (request.getParameter("profileUpdate") != null && request.getParameter("profileUpdate").equals("true")) {
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
            }

            doGet(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

}
