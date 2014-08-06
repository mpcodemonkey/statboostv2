package com.csc191;

import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

@WebServlet("/emailsqllist")
public class EmailSqllistServlet extends HttpServlet {
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String DB_URL = "jdbc:mysql://localhost/backend";
//    static Logger logger = Logger.


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_email";

        Connection connection = null;
        Properties connectionProperties = new Properties();
        ResultSet rs = null;
        try   {
            connection = DriverManager.getConnection(DB_URL, request.getParameter(PARAM_USERNAME), request.getParameter(PARAM_PASSWORD));

//            if(connection == null)  {
//                ("Your username and password do not match our records.");
//            }

        } catch (Exception e)  {
//            error.add("Your username and password do not match our records.");
//            forwardToLogin(request, response, error);
//            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
