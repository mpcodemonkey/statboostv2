package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Category;
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

/**
 * Created by Jessica on 11/15/14.
 */
@WebServlet("/admin/inventorycategoryeditor")
public class InventoryCategoryEditorServlet extends HttpServlet {
    public static final String SRV_MAP = "/admin/inventorycategoryeditor";
    public static final String PARAM_INVENTORY_CATEGORY_UID = "inventoryCategoryUid";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_INVENTORY_CATEGORY = "inventoryCategory";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String ATTR_CATEGORIES = "categories";
    public static final String PARAM_IS_DELETE = "isDelete";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        Category category = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        boolean isNew = false;
        if(request.getParameter(PARAM_INVENTORY_CATEGORY_UID) == null || request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("") ||
                request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("0"))  {
            //they are creating a new category
            category = new Category();
            isNew = true;
        } else  {
            //they want to edit/view a category
            category = (Category) session.load(Category.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_CATEGORY_UID)));
        }


        if(request.getParameter(PARAM_IS_DELETE) != null && request.getParameter(PARAM_IS_DELETE).equals("true"))  {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
            forwardToSqllist(request, response);
        } else if((category.getDeletable() != null && category.getDeletable() == 1) || isNew)  {
            forwardToEditor(request, response, category, null, "");
        } else  {
            forwardToSqllistWithWarning(request, response);
        }

        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        Category category = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(request.getParameter(PARAM_INVENTORY_CATEGORY_UID) == null || request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("") ||
                request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("0"))  {
            //should not get here, we are not going to let them create new webpages
            category = new Category();
        } else  {
            category = (Category) session.load(Category.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_CATEGORY_UID)));
        }

        category.setCategory(request.getParameter(PARAM_NAME));

        ArrayList<String> errors = new ArrayList<String>();
        if(category.getCategory() == null || category.getCategory().trim().equals(""))  {
            errors.add("You must enter a name for the category.");
        }

        //will always be 1 because if they get here it means they are creating a new one or editing one...which is obviously editable
        category.setDeletable(Byte.parseByte("1"));

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            session.save(category);
            session.getTransaction().commit();
            forwardToEditor(request, response, category, errors, "The record was save successfully.");
        }  else  {
            forwardToEditor(request, response, category, errors, "");
        }
        session.close();
    }


    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Category category,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_INVENTORY_CATEGORY, category);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("/admin/InventoryCategoryEditor.jsp").forward(request, response);
    }

    private static void forwardToSqllistWithWarning(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(InventoryCategorySqllistServlet.getInfoUrl("The record you selected cannot be edited because it is a default category."));
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(InventoryCategorySqllistServlet.getInfoUrl("The category has been deleted."));
    }

    public static String getEditUrl(int categoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_CATEGORY_UID + "=" + categoryUid;
    }

    public static String getDeleteUrl(int categoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_CATEGORY_UID + "=" + categoryUid + "&" + PARAM_IS_DELETE + "=true";
    }
}
