package com.csc191;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final String SRV_MAP = "/login";
    static Logger logger = Logger.getLogger(LoginServlet.class);
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String DB_URL = "jdbc:mysql://localhost/backend";
    public static final String ATTR_ERRORS = "errors";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> error = new ArrayList<String>();
        forwardToLogin(request, response, error);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> error = new ArrayList<String>();
        if(request.getParameter(PARAM_USERNAME) == null || request.getParameter(PARAM_USERNAME).equals(""))  {
            error.add("You must enter a username to login.");
        }

        if(request.getParameter(PARAM_PASSWORD) == null || request.getParameter(PARAM_PASSWORD).equals(""))  {
            error.add("You must enter a password to login.");
        }

        if(error.size() == 0)  {
            Connection connection = null;
            Properties connectionProperties = new Properties();
            ResultSet rs = null;
            try   {
                connection = DriverManager.getConnection(DB_URL, request.getParameter(PARAM_USERNAME), request.getParameter(PARAM_PASSWORD));

                if(connection == null)  {
                    error.add("Your username and password do not match our records.");
                }

            } catch (Exception e)  {
                error.add("Your username and password do not match our records.");
                forwardToLogin(request, response, error);
                return;
            }
        }

        if(error.size() > 0)  {
            forwardToLogin(request, response, error);
        } else  {
            forwardToHome(request, response);
        }
    }

    private static void forwardToLogin(HttpServletRequest request, HttpServletResponse response, ArrayList<String> error)
            throws IOException, ServletException {
        request.setAttribute(ATTR_ERRORS, error);
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    private static void forwardToHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }
}
