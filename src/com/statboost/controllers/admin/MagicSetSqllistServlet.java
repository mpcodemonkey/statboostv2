package com.statboost.controllers.admin;

import com.statboost.models.actor.User;
import com.statboost.models.form.Webpage;
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
import java.util.List;

/**
 * Created by Jessica on 10/20/14.
 */
@WebServlet("/admin/magicsetsqllist")
public class MagicSetSqllistServlet extends HttpServlet {
    public static final String ATTR_SETS = "sets";
    public static final String SRV_MAP = "/admin/magicsetsqllist";
    static Logger logger = Logger.getLogger(MagicSetSqllistServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        String hql = "from MagicSet";
        List webpages = (List<Webpage>)ServletUtil.getListFromHql(hql);
        forwardToSqllist(request, response, webpages);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, List webpages)
            throws IOException, ServletException {
        HttpSession userSession = request.getSession(false); //obtain the session object if exists
        if (!User.isAdmin(userSession)) {
            response.sendRedirect("/");
            return;
        }

        request.setAttribute(ATTR_SETS, webpages);
        request.getRequestDispatcher("/admin/MagicSetSqllist.jsp").forward(request, response);
    }

}
