package com.statboost.controllers.admin;

import com.statboost.models.inventory.Inventory;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by Jessica on 9/25/14.
 */

@WebServlet("/admin/inventorysqllist")
public class InventorySqllistServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventorySqllistServlet.class);
    public static final String SRV_MAP = "/admin/inventorysqllist";
    public static final String ATTR_INVENTORY = "inventory";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo: pagination
        String sql = "select * from stt_inventory limit 10";
        ResultSet inventory = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, inventory);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet inventory)
            throws IOException, ServletException {
        request.setAttribute(ATTR_INVENTORY, inventory);
        request.getRequestDispatcher("/InventorySqllist.jsp").forward(request, response);
    }
}
