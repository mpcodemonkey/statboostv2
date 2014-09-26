package com.statboost.controllers.admin;

import com.statboost.models.inventory.Condition;
import com.statboost.models.inventory.ConditionInventoryLink;
import com.statboost.models.inventory.Inventory;
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
import java.util.List;

/**
 * Created by Jessica on 9/25/14.
 */

@WebServlet("/admin/inventoryeditor")
public class InventoryEditorServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventoryEditorServlet.class);
    public static final String SRV_MAP = "/admin/inventoryeditor";
    public static final String ATTR_EVENT = "event";
    public static final String ATTR_MAGIC_CARD = "magicCard";
    public static final String ATTR_YUGIOH_CARD = "yugiohCard";
    public static final String ATTR_INVENTORY = "inventory";
    public static final String PARAM_INVENTORY_UID = "inventoryUid";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_IMAGE = "image";
    public static final String PARAM_PRE_ORDER = "preOrder";
    public static final String PARAM_DESCRIPTION = "desciption";
    public static final String ATTR_CONDITIONS = "conditions";
    public static final String ATTR_CONDITION_INVENTORY_LINKS = "conditionInventoryLinks";
    //todo: figure out a way to get all of the number in stock based on condition

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Inventory inventory = null;
        SessionFactory sessionFactory = HibernateUtil.getDatabaseSessionFactory();
        Session session = sessionFactory.openSession();
        List<ConditionInventoryLink> conditionInventoryLinks = null;
        if(request.getParameter(PARAM_INVENTORY_UID) == null || request.getParameter(PARAM_INVENTORY_UID).equals("0") ||
                request.getParameter(PARAM_INVENTORY_UID).equals("0"))  {
            inventory = new Inventory();
        } else  {
            inventory = (Inventory) session.load(Inventory.class, Integer.parseInt(request.getParameter(PARAM_INVENTORY_UID)));
        }

        List<Condition> conditions = (List<Condition>) session.createSQLQuery("select * from stt_condition").addEntity(Condition.class).list();


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
