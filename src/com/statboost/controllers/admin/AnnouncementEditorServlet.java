package com.statboost.controllers.admin;

import com.statboost.models.form.Announcement;
import com.statboost.util.HibernateUtil;
import com.statboost.util.ServletUtil;
import net.authorize.util.DateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 9/20/14.
 */
@WebServlet("/admin/announcementeditor")
public class AnnouncementEditorServlet extends HttpServlet {
    public static final String SRV_MAP = "/admin/announcementeditor";
    public static final String PARAM_ANNOUNCEMENT_UID = "announcementUid";
    public static final String PARAM_CREATED = "created";
    public static final String PARAM_TITLE = "title";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    public static final String ATTR_ANNOUNCEMENT = "announcement";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";

    public static final String PARAM_IS_DELETE = "isDelete";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo: should let them create, view, or delete an announcement, but not edit it
        Announcement announcement = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_ANNOUNCEMENT_UID) == null || request.getParameter(PARAM_ANNOUNCEMENT_UID).equals("") ||
                request.getParameter(PARAM_ANNOUNCEMENT_UID).equals("0"))  {
            announcement = new Announcement();
            forwardToEditor(request, response, announcement, null, "");
        } else if(request.getParameter(PARAM_IS_DELETE) != null && request.getParameter(PARAM_IS_DELETE).equals("true"))  {
            announcement = (Announcement) session.load(Announcement.class, Integer.parseInt(request.getParameter(PARAM_ANNOUNCEMENT_UID)));
            session.delete(announcement);
            session.getTransaction().commit();
            forwardToSqllist(request, response, announcement, "The announcement was successfully deleted.");
        } else  {
            announcement = (Announcement) session.load(Announcement.class, Integer.parseInt(request.getParameter(PARAM_ANNOUNCEMENT_UID)));
            forwardToView(request, response, announcement, "");
        }
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Announcement announcement = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_ANNOUNCEMENT_UID) == null || request.getParameter(PARAM_ANNOUNCEMENT_UID).equals("") ||
                request.getParameter(PARAM_ANNOUNCEMENT_UID).equals("0"))  {
            announcement = new Announcement();
        } else  {
            announcement = (Announcement) session.load(Announcement.class, Integer.parseInt(request.getParameter(PARAM_ANNOUNCEMENT_UID)));
        }

        announcement.setContent(request.getParameter(PARAM_BODY));
        announcement.setTitle(request.getParameter(PARAM_TITLE));
        announcement.setCreated(ServletUtil.getCurrentDate());

        ArrayList<String> errors = new ArrayList<String>();
        if(announcement.getContent() == null || announcement.getContent().equals(""))  {
            errors.add("You must enter the content of your announcement.");
        }

        if(announcement.getTitle() == null || announcement.getTitle().equals(""))  {
            errors.add("You must enter the title of your announcement.");
        }

        if(errors.size() == 0)  {
            session.save(announcement);
            session.getTransaction().commit();
            forwardToView(request, response, announcement, "The record was saved successfully!");
        } else  {
            forwardToEditor(request, response, announcement, errors, "");
        }

        session.close();

    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Announcement announcement,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_ANNOUNCEMENT, announcement);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/AnnouncementEditor.jsp").forward(request, response);
    }

    private static void forwardToView(HttpServletRequest request, HttpServletResponse response, Announcement announcement, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_ANNOUNCEMENT, announcement);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/ViewAnnouncement.jsp").forward(request, response);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, Announcement announcement, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_ANNOUNCEMENT, announcement);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/AnnouncementSqllist.jsp").forward(request, response);
    }

    public static String getEditUrl(int announcementUid)  {
        return SRV_MAP + "?" + PARAM_ANNOUNCEMENT_UID + "=" + announcementUid;
    }

    public static String getDeleteUrl(int announcementUid)  {
        return SRV_MAP + "?" + PARAM_ANNOUNCEMENT_UID + "=" + announcementUid + "&" + PARAM_IS_DELETE + "=true";
    }
}
