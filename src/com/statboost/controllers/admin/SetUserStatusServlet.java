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


@WebServlet("/admin/setUserStatus")
public class SetUserStatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); //obtain the session object if exists
        //validate the user
        if (User.isAdmin(session)) {

            if (request.getParameter("action")!=null && request.getParameter("action").equalsIgnoreCase("activate")) {
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
                //going to be activating
                request.setAttribute("actionType", "Activate");
                request.getRequestDispatcher("SetUserStatus.jsp").forward(request, response);

            } else if (request.getParameter("action")!=null && request.getParameter("action").equalsIgnoreCase("deactivate")) {
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
                //going to be deactivating
                request.setAttribute("actionType", "Deactivate");
                request.getRequestDispatcher("SetUserStatus.jsp").forward(request, response);

            } else {
                response.sendRedirect("/admin/adminCP"); //bad get parameter
            }



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

            for (String s : deleteList) {
                User user = User.find(s);
                if (user == null) {
                    System.err.println("Oh crap, couldn't find this user to delete: " + s);
                } else {
                    //toggle active flag as byte value
                    if (user.getUsrActive().intValue() == 127) {
                        user.setUsrActive(new Byte(Byte.MIN_VALUE)); //set false
                    } else {
                        user.setUsrActive(new Byte(Byte.MAX_VALUE)); //set true
                    }
                    userUpdateList.add(user);
                }
            }

            User.updateUsers(userUpdateList); //database update

        } else {
            response.sendRedirect("/");
        }
    }

}
