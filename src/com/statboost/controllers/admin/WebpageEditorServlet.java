package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.form.Webpage;
import com.statboost.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/webpageeditor")
public class WebpageEditorServlet extends HttpServlet {
    public static final String SRV_MAP = "/admin/webpageeditor";
    public static final String PARAM_WEBPAGE_UID = "webpageUid";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_WEBPAGE = "webpage";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String PARAM_CODE = "code";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        Webpage webpage = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        if(request.getParameter(PARAM_WEBPAGE_UID) == null || request.getParameter(PARAM_WEBPAGE_UID).equals("") ||
                request.getParameter(PARAM_WEBPAGE_UID).equals("0"))  {
            //should not get here, we are not going to let them create new webpages
            webpage = new Webpage();
        } else  {
            webpage = (Webpage) session.load(Webpage.class, Integer.parseInt(request.getParameter(PARAM_WEBPAGE_UID)));
        }

        forwardToEditor(request, response, webpage, null, "");
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        Webpage webpage = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_WEBPAGE_UID) == null || request.getParameter(PARAM_WEBPAGE_UID).equals("") ||
                request.getParameter(PARAM_WEBPAGE_UID).equals("0"))  {
            //should not get here because we are not allowing the user to create new web pages
            webpage = new Webpage();
        } else  {
            webpage = (Webpage) session.load(Webpage.class, Integer.parseInt(request.getParameter(PARAM_WEBPAGE_UID)));
        }

        webpage.setBody(request.getParameter(PARAM_BODY));
        webpage.setCode(request.getParameter(PARAM_CODE));
        webpage.setName(request.getParameter(PARAM_NAME));

        ArrayList<String> errors = new ArrayList<String>();
        if(webpage.getBody() == null || webpage.getBody().trim().equals(""))  {
            errors.add("You must enter content of the webpage.");
        }

        if(webpage.getName() == null || webpage.getName().trim().equals(""))  {
            errors.add("You must enter a name for the webpage.");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            session.save(webpage);
            session.getTransaction().commit();
            forwardToEditor(request, response, webpage, errors, "The record was save successfully.");
        }  else  {
            forwardToEditor(request, response, webpage, errors, "");
        }
        session.close();
    }


    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Webpage webpage,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_WEBPAGE, webpage);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/admin/WebpageEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(int webpageUid)  {
        return SRV_MAP + "?" + PARAM_WEBPAGE_UID + "=" + webpageUid;
    }
}
