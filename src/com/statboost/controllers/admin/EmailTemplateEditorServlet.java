package com.statboost.controllers.admin;

import com.statboost.models.email.EmailTemplate;
import com.statboost.models.email.EmailVariable;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/emailtemplateeditor")
public class EmailTemplateEditorServlet extends HttpServlet  {
    static Logger logger = Logger.getLogger(EmailTemplateEditorServlet.class);
    public static final String SRV_MAP = "/admin/emailtemplateeditor";
    public static final String PARAM_EMAIL_TEMPLATE_UID = "emailTemplateUid";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_EMAIL_TEMPLATE = "emailTemplate";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String ATTR_EMAIL_VARIABLES = "emailVariables";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmailTemplate emailTemplate = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) == null || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals("")
                || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals("0"))  {
            emailTemplate = new EmailTemplate();
        } else  {
            emailTemplate = (EmailTemplate) session.load(EmailTemplate.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_TEMPLATE_UID)));
        }

        ResultSet emailVariableGroupUid = ServletUtil.getResultSetFromSql("select evg_uid from stt_email_variable_group where evg_name='Company'");
        List<EmailVariable> emailVariables = null;
        try {
            if(emailVariableGroupUid.next())  {
                emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + emailVariableGroupUid.getInt("evg_uid")).addEntity(EmailVariable.class).list();
            }
        } catch (SQLException e) {
            logger.error("Could not get the email variable group uid.", e);
        }


        forwardToEditor(request, response, emailTemplate, null, "", emailVariables);
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmailTemplate emailTemplate = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) == null || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals("")
                || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals("0"))  {
            emailTemplate = new EmailTemplate();
        } else  {
            emailTemplate = (EmailTemplate) session.load(EmailTemplate.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_TEMPLATE_UID)));
        }

        ResultSet emailVariableGroupUid = ServletUtil.getResultSetFromSql("select evg_uid from stt_email_variable_group where evg_name='Company'");
        List<EmailVariable> emailVariables = null;
        try {
            if(emailVariableGroupUid.next())  {
                emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + emailVariableGroupUid.getInt("evg_uid")).addEntity(EmailVariable.class).list();
            }
        } catch (SQLException e) {
            logger.error("Could not get the email variable group uid.", e);
        }

        emailTemplate.setBody(request.getParameter(PARAM_BODY));
        emailTemplate.setName(request.getParameter(PARAM_NAME));

        ArrayList<String> errors = new ArrayList<String>();
        if(emailTemplate.getBody() == null || emailTemplate.getBody().trim().equals(""))  {
            errors.add("You must enter content of the email template.");
        }

        if(emailTemplate.getName() == null || emailTemplate.getName().trim().equals(""))  {
            errors.add("You must enter a name for the email template.");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            session.save(emailTemplate);
            session.getTransaction().commit();
            forwardToEditor(request, response, emailTemplate, errors, "The record was saved successfully.", emailVariables);
        }  else  {
            forwardToEditor(request, response, emailTemplate, errors, "", emailVariables);
        }

        session.close();
    }

    public static String getEditUrl(int emailTemplateUid)  {
        return SRV_MAP + "?" + PARAM_EMAIL_TEMPLATE_UID + "=" + emailTemplateUid;
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, EmailTemplate emailTemplate,
                                        ArrayList<String> errors, String info, List<EmailVariable> emailVariables)
            throws IOException, ServletException {
        request.setAttribute(ATTR_EMAIL_TEMPLATE, emailTemplate);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.setAttribute(ATTR_EMAIL_VARIABLES, emailVariables);
        request.getRequestDispatcher("/admin/EmailTemplateEditor.jsp").forward(request, response);
    }
}
