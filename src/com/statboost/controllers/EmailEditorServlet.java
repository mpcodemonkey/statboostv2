package com.statboost.controllers;

import com.statboost.models.email.Email;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/emaileditor")
public class EmailEditorServlet extends HttpServlet {
    public static final String SRV_MAP = "/emaileditor";
    public static final String PARAM_EMAIL_UID = "emailUid";
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_EMAIL_TEMPLATE = "emailTemplate";
    static Logger logger = Logger.getLogger(EmailEditorServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Email email = null;
        if(request.getParameter(PARAM_EMAIL_UID) == null || request.getParameter(PARAM_EMAIL_UID).equals(""))  {
            email = new Email();
        } else  {
            //todo: load up the email obj from the db using Hybrid or ERM
            email = new Email();
        }

        //load up all of the email templates so they can select from a dropdown
        ResultSet emailTemplates = ServletUtil.getResultSetFromSql("select * from stt_email_template;");

        //todo: decide if we need to load up the variable group or not, workflow event?
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static String getEditUrl(int emailUid)  {
        return SRV_MAP + "?" + PARAM_EMAIL_UID + "=" + emailUid;
    }


}
