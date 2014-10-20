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
 * Created by Jessica on 10/20/14.
 */
@WebServlet("/admin/magicsetsqllist")
public class MagicSetSqllistServlet extends HttpServlet {
    public static final String ATTR_SETS = "sets";
    public static final String SRV_MAP = "/admin/magicsetsqllist";
    static Logger logger = Logger.getLogger(MagicSetSqllistServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_magic_set";
        ResultSet webpages = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, webpages);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet webpages)
            throws IOException, ServletException {
        request.setAttribute(ATTR_SETS, webpages);
        request.getRequestDispatcher("/admin/MagicSetSqllist.jsp").forward(request, response);
    }

}
