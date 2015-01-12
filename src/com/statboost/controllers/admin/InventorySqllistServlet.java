package com.statboost.controllers.admin;

import com.statboost.models.DAO.GenericDAO;
import com.statboost.models.actor.User;
import com.statboost.models.inventory.Inventory;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica on 9/25/14.
 */

@WebServlet("/admin/inventorysqllist")
public class InventorySqllistServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    public static final String SRV_MAP = "/admin/inventorysqllist";
    public static final String ATTR_INVENTORY = "inventory";
    public static final String PARAM_KEYWORD = "keyword";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }
        GenericDAO inventoryDAO = new GenericDAO();
        String kw = request.getParameter(PARAM_KEYWORD);
        String hql = "FROM Inventory where name like '%" + kw + "%'";

        ArrayList<Inventory> inventory = (ArrayList<Inventory>)ServletUtil.getListFromHql(hql);
        if(inventory == null) {
            logger.error("could not retrieve result set in inventorysqllist");
        }
        forwardToSqllist(request, response, inventory);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, List inventory)
            throws IOException, ServletException {
        request.setAttribute(ATTR_INVENTORY, inventory);
        request.getRequestDispatcher("/admin/InventorySqllist.jsp").forward(request, response);
    }
}
