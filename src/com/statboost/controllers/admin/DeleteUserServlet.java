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
import java.util.List;


@WebServlet("/admin/deleteUser")
public class DeleteUserServlet extends HttpServlet {

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




            request.getRequestDispatcher("DeleteUser.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //validate the user
        if (User.isAdmin(session)) {

            String[] deleteList = request.getParameterValues("deleteList");
            for (String s : deleteList) {
                System.out.println(s);
            }

        } else {
            response.sendRedirect("/");
        }
    }

}
