package com.statboost.controllers.admin;

import com.statboost.models.email.*;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
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
    static Logger logger = Logger.getLogger(EmailEditorServlet.class);
    public static final String SRV_MAP = "/admin/emaileditor";
    public static final String PARAM_EMAIL_UID = "emailUid";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SUBJECT = "subject";
    public static final String PARAM_EMAIL_TEMPLATE_UID = "emailTemplateUid";
    public static final String PARAM_EMAIL_VARIABLE_GROUP_UID = "emailVariableGroupUid";
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_EMAIL_TEMPLATE = "emailTemplate";
    public static final String ATTR_EMAIL_VARIABLES = "emailVariables";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String ATTR_WORKFLOW_EVENT = "workflowEvent";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Email email = null;
        List<EmailVariable> emailVariables = null;
        WorkflowEvent workflowEvent = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        if(request.getParameter(PARAM_EMAIL_UID) == null || request.getParameter(PARAM_EMAIL_UID).equals("") ||
                request.getParameter(PARAM_EMAIL_UID).equals("0"))  {
            //should not ever get here, this would mean we were allowing them to create a new email
            email = new Email();
        } else  {
            //load up the email obj from the db
            email = (Email) session.load(Email.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_UID)));
            emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + email.getUid()).addEntity(EmailVariable.class).list();
            List<EmailWorkflowEventLink> emailWorkflowEventLink = (List<EmailWorkflowEventLink>) session.createSQLQuery("select * from stt_email_workflow_event_link where ewe_eml_uid = " + email.getUid()).addEntity(EmailWorkflowEventLink.class).list();
            workflowEvent = (WorkflowEvent) session.load(WorkflowEvent.class, emailWorkflowEventLink.get(0).getEmailWorkflowEvent().getUid());
        }

        //load up all of the email templates so they can select from a dropdown
        List<EmailTemplate> emailTemplates = session.createSQLQuery("select * from stt_email_template").addEntity(EmailTemplate.class).list();

       forwardToEditor(request, response, email, emailTemplates, emailVariables, null, null, workflowEvent);

       session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Email email = null;
        List<EmailVariable> emailVariables = null;
        WorkflowEvent workflowEvent = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_EMAIL_UID) == null || request.getParameter(PARAM_EMAIL_UID).equals("") ||
                request.getParameter(PARAM_EMAIL_UID).equals("0"))  {
            //should not ever get here, this would mean we were allowing them to create a new email
            email = new Email();
        } else  {
            //load up the email obj from the db
            email = (Email) session.load(Email.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_UID)));
            emailVariables = (List<EmailVariable>) session.createSQLQuery("select * from stt_email_variable where evr_evg_uid = " + email.getUid()).addEntity(EmailVariable.class).list();
            List<EmailWorkflowEventLink> emailWorkflowEventLink = (List<EmailWorkflowEventLink>) session.createSQLQuery("select * from stt_email_workflow_event_link where ewe_eml_uid = " + email.getUid()).addEntity(EmailWorkflowEventLink.class).list();
            workflowEvent = (WorkflowEvent) session.load(WorkflowEvent.class, emailWorkflowEventLink.get(0).getEmailWorkflowEvent().getUid());
        }

        //load up all of the email templates so they can select from a dropdown
        List<EmailTemplate> emailTemplates = session.createSQLQuery("select * from stt_email_template").addEntity(EmailTemplate.class).list();;

        email.setBody(request.getParameter(PARAM_BODY));
        email.setName(request.getParameter(PARAM_NAME));
        email.setSubject(request.getParameter(PARAM_SUBJECT));

        ArrayList<String> errors = new ArrayList<String>();
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) != null && !request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals(""))  {
            EmailTemplate emailTemplate = (EmailTemplate) session.load(EmailTemplate.class, Integer.parseInt(request.getParameter(PARAM_EMAIL_TEMPLATE_UID)));
            email.setEmailTemplate(emailTemplate);
        } else  {
            //should never get here because we do not give them the option to not select an email template
            errors.add("You must select an email template.");
        }

        if(email.getBody() == null || email.getBody().trim().equals(""))  {
            errors.add("You must enter content of the email.");
        }

        if(email.getName() == null || email.getName().trim().equals(""))  {
            errors.add("You must enter a name for the email.");
        }

        if(email.getSubject() == null || email.getSubject().trim().equals(""))  {
            errors.add("You must enter a subject for the email.");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            session.save(email);
            session.getTransaction().commit();
            forwardToEditor(request, response, email, emailTemplates, emailVariables, errors, "The record was saved successfully.", workflowEvent);
        } else  {
            forwardToEditor(request, response, email, emailTemplates, emailVariables, errors, "", workflowEvent);
        }

        session.close();
    }

    public static String getEditUrl(int emailUid)  {
        return SRV_MAP + "?" + PARAM_EMAIL_UID + "=" + emailUid;
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Email email,
                                        List<EmailTemplate> emailTemplates, List<EmailVariable> emailVariables, ArrayList<String> errors,
                                        String info, WorkflowEvent workflowEvent)
            throws IOException, ServletException {
        request.setAttribute(ATTR_EMAIL, email);
        request.setAttribute(ATTR_EMAIL_TEMPLATE, emailTemplates);
        request.setAttribute(ATTR_EMAIL_VARIABLES, emailVariables);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.setAttribute(ATTR_WORKFLOW_EVENT, workflowEvent);
        request.getRequestDispatcher("/admin/EmailEditor.jsp").forward(request, response);
    }
}
