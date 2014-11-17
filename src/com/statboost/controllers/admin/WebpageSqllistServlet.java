package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/admin/webpagesqllist")
public class WebpageSqllistServlet extends HttpServlet {
    public static final String ATTR_WEBPAGES = "webpages";
    public static final String SRV_MAP = "/admin/webpagesqllist";
    static Logger logger = Logger.getLogger(WebpageSqllistServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        String sql = "select * from stt_webpage";
        ResultSet webpages = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, webpages);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet webpages)
            throws IOException, ServletException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        request.setAttribute(ATTR_WEBPAGES, webpages);
        request.getRequestDispatcher("/admin/WebpagesSqllist.jsp").forward(request, response);
    }
}
