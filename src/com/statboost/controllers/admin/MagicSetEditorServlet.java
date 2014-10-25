package com.statboost.controllers.admin;

import com.statboost.models.form.Webpage;
import com.statboost.models.mtg.MagicSet;
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
import java.util.ArrayList;

/**
 * Created by Jessica on 10/20/14.
 */
@WebServlet("/admin/magicseteditor")
public class MagicSetEditorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(MagicSetEditorServlet.class);
    public static final String SRV_MAP = "/admin/magicseteditor";
    public static final String PARAM_MAGIC_SET_UID = "magicSetUid";
    public static final String PARAM_NAME = "magicSetName";
    public static final String PARAM_RELEASE_DATE = "releaseDate";
    public static final String PARAM_BORDER = "border";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_BLOCK = "block";

    public static final String ATTR_MAGIC_SET = "magicSet";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MagicSet magicSet = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();

        if(request.getParameter(PARAM_MAGIC_SET_UID) == null || request.getParameter(PARAM_MAGIC_SET_UID).equals("")  ||
                request.getParameter(PARAM_MAGIC_SET_UID).equals("0"))  {
            magicSet = new MagicSet();
        } else  {
            magicSet = (MagicSet) session.load(MagicSet.class, request.getParameter(PARAM_MAGIC_SET_UID));
        }

        forwardToEditor(request, response, magicSet, null, "");
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MagicSet magicSet = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();

        if(request.getParameter(PARAM_MAGIC_SET_UID) == null || request.getParameter(PARAM_MAGIC_SET_UID).equals("")  ||
                request.getParameter(PARAM_MAGIC_SET_UID).equals("0"))  {
            magicSet = new MagicSet();
        } else  {
            magicSet = (MagicSet) session.load(MagicSet.class, Integer.parseInt(request.getParameter(PARAM_MAGIC_SET_UID)));
        }

        magicSet.setMstBlock(request.getParameter(PARAM_BLOCK));
        magicSet.setMstBorder(request.getParameter(PARAM_BORDER));
        magicSet.setMstName(request.getParameter(PARAM_NAME));
        magicSet.setMstReleaseDate(ServletUtil.getDateFromString(request.getParameter(PARAM_RELEASE_DATE)));
        magicSet.setMstType(request.getParameter(PARAM_TYPE));

        ArrayList<String> errors = new ArrayList<String>();

        //todo: figure out what fields are required
        if(magicSet.getMstName() == null || magicSet.getMstName().equals(""))  {
            errors.add("You must enter the name of the set");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            session.save(magicSet);
            session.getTransaction().commit();
            forwardToEditor(request, response, magicSet, errors, "The record was save successfully.");
        }  else  {
            forwardToEditor(request, response, magicSet, errors, "");
        }
        session.close();

    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, MagicSet magicSet,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_MAGIC_SET, magicSet);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/admin/MagicSetEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(String magicSetUid)  {
        return SRV_MAP + "?" + PARAM_MAGIC_SET_UID + "=" + magicSetUid;
    }

}
