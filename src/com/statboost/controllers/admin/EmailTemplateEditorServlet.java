package com.statboost.controllers.admin;

import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/emailtemplateeditor")
public class EmailTemplateEditorServlet {
    static Logger logger = Logger.getLogger(EmailTemplateEditorServlet.class);
    public static final String SRV_MAP = "/emailtemplateeditor";
    public static final String PARAM_EMAIL_TEMPLATE_UID = "emailTemplateUid";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_EMAIL_TEMPLATE = "emailTemplate";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmailTemplate emailTemplate = null;
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) == null || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals(""))  {
            emailTemplate = new EmailTemplate();
        } else  {
            emailTemplate = (EmailTemplate) ServletUtil.getObjectFromSql("select * from stt_email_template where " +
                    "etm_uid = " + request.getParameter(PARAM_EMAIL_TEMPLATE_UID));
        }

        forwardToEditor(request, response, emailTemplate, null, "");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmailTemplate emailTemplate = null;
        if(request.getParameter(PARAM_EMAIL_TEMPLATE_UID) == null || request.getParameter(PARAM_EMAIL_TEMPLATE_UID).equals(""))  {
            emailTemplate = new EmailTemplate();
        } else  {
            emailTemplate = (EmailTemplate) ServletUtil.getObjectFromSql("select * from stt_email_template where " +
                    "etm_uid = " + request.getParameter(PARAM_EMAIL_TEMPLATE_UID));
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
            ServletUtil.saveObject(emailTemplate);
            forwardToEditor(request, response, emailTemplate, errors, "The record was save successfully.");
        }  else  {
            forwardToEditor(request, response, emailTemplate, errors, "");
        }
    }

    public static String getEditUrl(int emailTemplateUid)  {
        return SRV_MAP + "?" + PARAM_EMAIL_TEMPLATE_UID + "=" + emailTemplateUid;
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, EmailTemplate emailTemplate,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_EMAIL_TEMPLATE, emailTemplate);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("EmailTemplateEditor.jsp").forward(request, response);
    }
}
