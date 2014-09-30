package com.statboost.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.statboost.models.actor.User;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.hibernate.*;

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

            } else if (request.getParameter("customerSearch") != null) {
                List<User> result = null;
                SessionFactory dbFactory = HibernateUtil.getDatabaseSessionFactory();
                Session dbSession = dbFactory.openSession();
                String hqlName = "From User where usrFirstName like :name and usrRole='Customer' order by usrLastName desc";
                String hqlEmail = "From User where usrEmail like :email and usrRole='Customer' order by usrLastName desc";
                try {
                    //query by name
                    Query query = dbSession.createQuery(hqlName);
                    query.setParameter("name", ServletUtil.sanitizeString(request.getParameter("term")));
                    query.setMaxResults(4);
                    result = query.list();

                    //query by email
                    Query query2 = dbSession.createQuery(hqlEmail);
                    query2.setParameter("email", ServletUtil.sanitizeString(request.getParameter("term")));
                    query2.setMaxResults(4);
                    result.addAll(query2.list());
                } catch (HibernateException e) {
                    e.printStackTrace();
                } finally {
                    dbSession.close();
                }
                //Build and send json payload from query results
                if (result != null) {
                    JsonArray j = new JsonArray();
                    for(User u : result) {
                        JsonObject jo = new JsonObject();
                        jo.addProperty("id", u.getUsrUid());
                        jo.addProperty("name", u.getUsrFirstName() + " " + u.getUsrLastName());
                        jo.addProperty("email", u.getUsrEmail());
                        j.add(jo);
                    }
                    response.getWriter().write(new Gson().toJson(j));
                }

            } else if (request.getParameter("customerID") != null) {
                String user_uid_str = request.getParameter("customerID");
                int user_uid = -1;
                boolean validParam = true;
                try {
                    user_uid = Integer.parseInt(user_uid_str);
                } catch (NumberFormatException e) {
                    validParam = false;
                }
                if (validParam) {
                    User customer = null;
                    SessionFactory dbFactory = HibernateUtil.getDatabaseSessionFactory();
                    Session dbSession = dbFactory.openSession();
                    String hql = "From User where usrUid = :id";
                    try {
                        Query query = dbSession.createQuery(hql);
                        query.setParameter("id", user_uid);
                        customer = (User)query.uniqueResult();
                    } catch (HibernateException e) {
                        e.printStackTrace();
                    } finally {
                        dbSession.close();
                    }

                    if (customer != null) {
                        if (customer.getUsrActive() == Byte.MAX_VALUE) {
                            request.setAttribute("actionType", "Deactivate");
                        } else {
                            request.setAttribute("actionType", "Activate");
                        }
                        request.setAttribute("customer", customer);
                        request.getRequestDispatcher("SetCustomerStatus.jsp").forward(request, response);
                    } else {
                        request.setAttribute("alert", "Sorry, could not find a user with the id: " + user_uid);
                        request.setAttribute("alertType", "danger");
                        request.getRequestDispatcher("AdminCP.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("alert", "Sorry, the provided parameter is invalid: " + user_uid_str);
                    request.setAttribute("alertType", "danger");
                    request.getRequestDispatcher("AdminCP.jsp").forward(request, response);
                }

            } else {
                request.getRequestDispatcher("AdminCP.jsp").forward(request, response);
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
