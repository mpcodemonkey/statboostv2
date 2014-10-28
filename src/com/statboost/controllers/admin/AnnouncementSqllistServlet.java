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
 * Created by Jessica on 9/20/14.
 */
@WebServlet("/admin/announcementsqllist")
public class AnnouncementSqllistServlet extends HttpServlet {
    public static final String ATTR_ANNOUNCEMENTS = "announcements";
    public static final String SRV_MAP = "/admin/announcementsqllist";
    static Logger logger = Logger.getLogger(AnnouncementSqllistServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_announcement";
        ResultSet announcements = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, announcements);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet announcements)
            throws IOException, ServletException {
        request.setAttribute(ATTR_ANNOUNCEMENTS, announcements);
        request.getRequestDispatcher("/admin/AnnouncementSqllist.jsp").forward(request, response);
    }
}
