package com.statboost.controllers.admin;

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
 * Created by Jessica on 11/15/14.
 */
@WebServlet("/admin/inventorycategorysqllist")
public class InventoryCategorySqllistServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventoryCategorySqllistServlet.class);
    public static final String SRV_MAP = "/admin/inventorycategorysqllist";
    public static final String ATTR_INVENTORY_CATEGORY = "inventoryCategory";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_category";
        ResultSet category = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, category);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet category)
            throws IOException, ServletException {
        request.setAttribute(ATTR_INVENTORY_CATEGORY, category);
        request.getRequestDispatcher("/admin/InventoryCategorySqllist.jsp").forward(request, response);
    }
}
