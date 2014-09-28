package com.statboost.controllers.admin;

import com.statboost.models.form.Announcement;
import com.statboost.models.inventory.Condition;
import com.statboost.util.HibernateUtil;
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
 * Created by Jessica on 9/28/14.
 */
@WebServlet("/admin/conditioneditor")
public class ConditionEditorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(ConditionEditorServlet.class);
    public static final String SRV_MAP = "/admin/conditioneditor";
    public static final String ATTR_CONDITION = "condition";
    public static final String PARAM_CONDITION_UID = "conditionUid";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_PERCENT_OF_PRICE = "percentOfPrice";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Condition condition = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_CONDITION_UID) == null || request.getParameter(PARAM_CONDITION_UID).equals("") ||
                request.getParameter(PARAM_CONDITION_UID).equals("0"))  {
            //should not get here, we don't want them adding conditions
            condition = new Condition();
        } else  {
            condition = (Condition) session.load(Condition.class, Integer.parseInt(request.getParameter(PARAM_CONDITION_UID)));
        }

        forwardToEditor(request, response, condition, null, "");
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Condition condition = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_CONDITION_UID) == null || request.getParameter(PARAM_CONDITION_UID).equals("") ||
                request.getParameter(PARAM_CONDITION_UID).equals("0"))  {
            //should not get here, we don't want them adding conditions
            condition = new Condition();
        } else  {
            condition = (Condition) session.load(Condition.class, Integer.parseInt(request.getParameter(PARAM_CONDITION_UID)));
        }

        ArrayList<String> errors = new ArrayList<String>();
        try  {
            condition.setPercentOfPrice(Double.parseDouble(request.getParameter(PARAM_PERCENT_OF_PRICE)));
        } catch(Exception e)  {
            errors.add("You must enter a number for the percent of price");
        }

        if(errors.size() == 0)  {
            session.save(condition);
            session.getTransaction().commit();
            forwardToEditor(request, response, condition, null, "The record was saved successfully.");
        } else  {
            forwardToEditor(request, response, condition, errors, "");
        }

        session.close();
    }

    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Condition condition,
                                        ArrayList<String> errors, String info) throws ServletException, IOException {
        request.setAttribute(ATTR_CONDITION, condition);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/admin/ConditionEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(int conditionUid)  {
        return SRV_MAP + "?" + PARAM_CONDITION_UID + "=" + conditionUid;
    }

}
