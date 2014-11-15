package com.statboost.controllers.admin;

import com.statboost.models.inventory.Category;
import com.statboost.models.inventory.InventoryCategory;
import com.statboost.util.HibernateUtil;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        if(request.getParameter(PARAM_INVENTORY_CATEGORY_UID) == null || request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("") ||
                request.getParameter(PARAM_INVENTORY_CATEGORY_UID).equals("0"))  {
            //should not get here, we are not going to let them create new webpages
            category = new Category();
        } else  {
            category = (Category) session.load(Category.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_CATEGORY_UID)));
        }

        forwardToEditor(request, response, category, null, "");
        session.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    public static String getEditUrl(int categoryUid)  {
        return SRV_MAP + "?" + PARAM_INVENTORY_CATEGORY_UID + "=" + categoryUid;
    }

}
