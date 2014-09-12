package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/admin/setUserInactive")
public class SetUserInactiveServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //validate the user
        if (User.isAdmin(session)) {


            //get list of admins and employees
            List<User> employeeUserList=null, adminUserList=null;
            Session userDB = HibernateUtil.getDatabaseSessionFactory().openSession();
            Transaction tx = null;
            try{
                tx = userDB.beginTransaction();
                employeeUserList = userDB.createQuery("FROM User WHERE usrRole='Employee'").list();
                adminUserList = userDB.createQuery("FROM User WHERE usrRole='Admin'").list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }  finally {
                userDB.close();
            }

            request.setAttribute("employees", employeeUserList);
            request.setAttribute("admins", adminUserList);




            request.getRequestDispatcher("SetUserInactive.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //validate the user
        if (User.isAdmin(session)) {

            String deleteListString = request.getParameter("selectList");
            String[] deleteList = deleteListString.split(",");
            List<User> userUpdateList = new ArrayList<>();

            boolean error = false;
            String errorList = "";
            for (String s : deleteList) {
                User user = User.find(s);
                if (user == null) {
                    error = true;
                    errorList += s+" ";
                } else {
                    user.setUsrActive(new Byte(Byte.MIN_VALUE)); //set false
                    userUpdateList.add(user);
                }
            }

            User.updateUsers(userUpdateList); //database update
/*
            if (error) {
                request.setAttribute("alert", "Something went wrong finding the following users: " + errorList + "<br>Please try again or contact an administrator.");
                request.setAttribute("alertType", "danger");
                doGet(request, response);

            } else {
                request.setAttribute("alert", "All the selected user accounts have been updated.");
                request.setAttribute("alertType", "success");
                doGet(request, response);
            }
*/

        } else {
            response.sendRedirect("/");
        }
    }

}
