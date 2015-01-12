package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.inventory.Category;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Jessica on 11/15/14.
 */
@WebServlet("/admin/inventorycategorysqllist")
public class InventoryCategorySqllistServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(InventoryCategorySqllistServlet.class);
    public static final String SRV_MAP = "/admin/inventorycategorysqllist";
    public static final String ATTR_INVENTORY_CATEGORY = "inventoryCategory";
    public static final String ATTR_INFO = "info";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        String hql = "from Category";
        List category = (List<Category>)ServletUtil.getListFromHql(hql);
        forwardToSqllist(request, response, category);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, List category)
            throws IOException, ServletException {
        request.setAttribute(ATTR_INVENTORY_CATEGORY, category);
        request.getRequestDispatcher("/admin/InventoryCategorySqllist.jsp").forward(request, response);
    }

    public static String getInfoUrl(String warning) throws UnsupportedEncodingException {
        return SRV_MAP + "?" + ATTR_INFO + "=" + URLEncoder.encode(warning, "UTF-8");
    }
}
