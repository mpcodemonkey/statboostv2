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
 * Created by Jessica on 9/28/14.
 */
@WebServlet("/admin/conditionsqllist")
public class ConditionSqllistServlet extends HttpServlet {
    public static Logger logger = Logger.getLogger(ConditionSqllistServlet.class);
    public static final String SRV_MAP = "/admin/conditionsqllist";
    public static final String ATTR_CONDITIONS = "conditions";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_condition";
        ResultSet conditions = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, conditions);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet conditions)
            throws IOException, ServletException {
        request.setAttribute(ATTR_CONDITIONS, conditions);
        request.getRequestDispatcher("/admin/ConditionSqllist.jsp").forward(request, response);
    }
}
