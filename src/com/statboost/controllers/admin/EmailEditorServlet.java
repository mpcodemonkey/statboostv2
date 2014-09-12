package com.statboost.controllers.admin;

import com.statboost.models.email.*;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/emaileditor")
public class EmailEditorServlet extends HttpServlet {
    public static final String SRV_MAP = "/admin/emaileditor";
    public static final String PARAM_EMAIL_UID = "emailUid";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_FROM = "from";
    public static final String PARAM_SUBJECT = "subject";
    public static final String PARAM_EMAIL_TEMPLATE_UID = "emailTemplateUid";
    public static final String PARAM_EMAIL_VARIABLE_GROUP_UID = "emailVariableGroupUid";
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_EMAIL_TEMPLATE = "emailTemplate";
    public static final String ATTR_EMAIL_VARIABLES = "emailVariables";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    static Logger logger = Logger.getLogger(EmailEditorServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Email email = null;
        List<EmailVariable> emailVariables = null;
        WorkflowEvent workflowEvent = null;
        if(request.getParameter(PARAM_EMAIL_UID) == null || request.getParameter(PARAM_EMAIL_UID).equals(""))  {
            //should not ever get here, this would mean we were allowing them to create a new email
            email = new Email();
        } else  {
            //load up the email obj from the db
            SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
            Session session = sessionFactory.openSession();
            email = (Email) session.load(Email.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_UID)));
            emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + email.getUid()).addEntity(EmailVariable.class).list();
        }

        //load up all of the email templates so they can select from a dropdown
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        List<EmailTemplate> emailTemplates = session.createSQLQuery("select * from stt_email_template").addEntity(EmailTemplate.class).list();
        //todo: decide if we need to load up the workflow event? - do this to display the workflow event name so that they know when ti is sent

       forwardToEditor(request, response, email, emailTemplates, emailVariables, null, null);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Email email = null;
        List<EmailVariable> emailVariables = null;
        if(request.getParameter(PARAM_EMAIL_UID) == null || request.getParameter(PARAM_EMAIL_UID).equals(""))  {
            //should not ever get here, this would mean we were allowing them to create a new email
            email = new Email();
        } else  {
            //load up the email obj from the db
            SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
            Session session = sessionFactory.openSession();
            email = (Email) session.load(Email.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_UID)));
            emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + email.getUid()).addEntity(EmailVariable.class).list();
        }

        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        List<EmailTemplate> emailTemplates = session.createSQLQuery("select * from stt_email_template").addEntity(EmailTemplate.class).list();

        email.setBody(request.getParameter(PARAM_BODY));
        email.setFrom(request.getParameter(PARAM_FROM));
        email.setName(request.getParameter(PARAM_NAME));
        email.setSubject(request.getParameter(PARAM_SUBJECT));

        ArrayList<String> errors = new ArrayList<String>();
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) != null && !request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals(""))  {
            email.setEmailTemplate((EmailTemplate) ServletUtil.getObjectFromSql("select * from stt_email_template where etm_uid = " + request.getParameter(PARAM_EMAIL_TEMPLATE_UID)));
        } else  {
            errors.add("You must select an email template.");
        }

        if(email.getBody() == null || email.getBody().trim().equals(""))  {
            errors.add("You must enter content of the email.");
        }

        if(email.getFrom() == null || email.getFrom().trim().equals(""))  {
            errors.add("You must enter the from email address for the email.");
        } else if(!ServletUtil.isEmailPattern(email.getFrom()))  {
            errors.add("You must enter a valid from email address.");
        }

        if(email.getName() == null || email.getName().trim().equals(""))  {
            errors.add("You must enter a name for the email.");
        }

        if(email.getSubject() == null || email.getSubject().trim().equals(""))  {
            errors.add("You must enter a subject for the email.");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            ServletUtil.saveObject(email);
            forwardToEditor(request, response, email, emailTemplates, emailVariables, errors, "The record was saved successfully.");
        } else  {
            forwardToEditor(request, response, email, emailTemplates, emailVariables, errors, "");
        }


    }

    public static String getEditUrl(int emailUid)  {
        return SRV_MAP + "?" + PARAM_EMAIL_UID + "=" + emailUid;
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Email email,
                                        List<EmailTemplate> emailTemplates, List<EmailVariable> emailVariables, ArrayList<String> errors,
                                        String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_EMAIL, email);
        request.setAttribute(ATTR_EMAIL_TEMPLATE, emailTemplates);
        request.setAttribute(ATTR_EMAIL_VARIABLES, emailVariables);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/EmailEditor.jsp").forward(request, response);
    }
}
